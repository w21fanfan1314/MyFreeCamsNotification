package com.cocos.mfcn.biz;

import com.cocos.mfcn.models.Gril;

import java.util.List;

/**
 * 监听
 */
public interface IWatchMyGrilService extends IMyGrilService{
    /**
     * 开始监听
     */
    boolean startWatch(final WatchListener listener);

    /**
     * 停止监听
     */
    void stopWatch();

    interface WatchListener
    {
        void someGrilsOnline(List<Gril> grils);
    }
}
