package com.cocos.mfcn.biz.impls;

import com.cocos.mfcn.biz.IWatchMyGrilService;
import com.cocos.mfcn.models.Gril;
import com.cocos.mfcn.models.GrilState;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.apache.bcel.generic.IF_ACMPEQ;

import java.util.*;

public class WatchMyGrilService extends MyGrilService implements IWatchMyGrilService {
    private Timer timer;
    private int delay = 60 * 5;
    private boolean isRequested;

    // 全部
    public List<Gril> myGrils;
    // 新上线的
    public List<Gril> onlineGrils;
    // 监听
    private WatchListener listener;

    @Override
    public boolean startWatch(final WatchListener listener) {
        if (null == timer)
        {
            timer = new Timer("watch_timer");
        }
        this.listener = listener;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isRequested)
                    refreshGrilForMyFreeCams();
            }
        },0, delay * 1000);
        return true;
    }

    @Override
    public void stopWatch() {
        timer.cancel();
        timer = null;
        this.listener = null;
    }

    @Override
    public boolean removeWatch(Gril gril) {
        boolean result = super.removeWatch(gril);
        indexMyGrils();
        return result;
    }

    @Override
    public boolean removeAllWatch() {
        boolean result = super.removeAllWatch();
        indexMyGrils();
        return result;
    }

    @Override
    public ObservableList<Gril> listMyGrils() {
        if (null == myGrils)
            this.indexMyGrils();
        return new ObservableListWrapper<>(myGrils);
    }

    @Override
    public Gril addWatch(Gril gril) {
        Gril newGril = super.addWatch(gril);
        indexMyGrils();
        return newGril;
    }

    private void refreshGrilForMyFreeCams()
    {
        isRequested = true;
        if (null == this.myGrils)
        {
            indexMyGrils();
        }
        this.onlineGrils = new ArrayList<>();

        if (null != myGrils && 0 < myGrils.size())
        {
            for (Gril gril : myGrils)
            {
                Gril newGril = super.addWatch(gril);
                if (gril.getState() == GrilState.OFF &&
                        newGril.getState() == GrilState.ONLINE)
                {
                    this.onlineGrils.add(newGril);
                }
            }
        }

        if (hasChange())
        {
            if (null != this.listener)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        WatchMyGrilService.this.listener.someGrilsOnline(WatchMyGrilService.this.onlineGrils);
                    }
                });

            indexMyGrils();
        }
        isRequested = false;
    }



    private void indexMyGrils() {
        ObservableList<Gril> grils = super.listMyGrils();
        myGrils = grils.subList(0, grils.size());
    }

    private boolean hasChange()
    {
        return  (null != onlineGrils && onlineGrils.size() > 0);
    }
}
