package com.cocos.mfcn.biz.impls;

import com.alibaba.fastjson.JSON;
import com.cocos.mfcn.biz.IMyGrilService;
import com.cocos.mfcn.cralwer.MyFreeCamsProfile;
import com.cocos.mfcn.cralwer.impls.MyFreeCamsProfileImpl;
import com.cocos.mfcn.cralwer.modals.GrilPageInfo;
import com.cocos.mfcn.models.Gril;
import com.cocos.mfcn.models.GrilState;
import com.cocos.mfcn.utils.GrilsDB;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyGrilService implements IMyGrilService {
    public static final String DB_NAME = "Grils_db";
    private MyFreeCamsProfile myFreeCamsProfile;
    private GrilsDB db;

    public MyGrilService() {
        this.myFreeCamsProfile = new MyFreeCamsProfileImpl();
        this.db = GrilsDB.shareInstance(DB_NAME);
        this.db.init();
    }

    /**
     * 添加关注时，只需要提供用户名即可
     * @param gril
     * @return
     */
    @Override
    public Gril addWatch(Gril gril) {
        GrilPageInfo grilPageInfo = this.myFreeCamsProfile.read(gril.getName());
        if (null != grilPageInfo)
        {
            Gril myGril = this.toMyGril(grilPageInfo);
            return this.watchMyGril(myGril);
        }
        return null;
    }

    @Override
    public boolean removeWatch(Gril gril) {
        String grilJson = this.db.delete(gril.getName());
        return (null != grilJson);
    }

    @Override
    public boolean removeAllWatch() {
        return this.db.deleteAll();
    }

    @Override
    public ObservableList<Gril> listMyGrils() {
        Map<String,String>  all = this.db.selectAll();
        List<Gril> grils = new ArrayList<>();
        if (null != all && 0 < all.size()){
            for (String grilJson : all.values())
            {
                if (null != grilJson && !grilJson.isEmpty())
                    grils.add(JSON.parseObject(grilJson, Gril.class));
            }
        }

        return new ObservableListWrapper<>(grils);
    }

    private Gril toMyGril(GrilPageInfo pageInfo)
    {
        Gril gril = new Gril();
        gril.setName(pageInfo.getUserName());
        gril.setState(GrilState.cast(pageInfo.getStatus()));
        gril.setImageUrl(pageInfo.getMainPhoto() == null ? pageInfo.getAvatar() : pageInfo.getMainPhoto());
        return gril;
    }


    private Gril watchMyGril(final Gril gril)
    {
        this.db.insert(gril.getName(), JSON.toJSONString(gril));
        return gril;
    }
}
