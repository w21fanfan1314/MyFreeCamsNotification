package com.cocos.mfcn.biz;

import com.cocos.mfcn.models.Gril;
import javafx.collections.ObservableList;

public interface IMyGrilService {
    /**
     * 关注
     * @param gril
     * @return
     */
    Gril addWatch(Gril gril);

    /**
     * 删除关注
     * @param gril
     * @return
     */
    boolean removeWatch(Gril gril);

    /**
     * 清楚所有的关注
     * @return
     */
    boolean removeAllWatch();

    /**
     * 查询
     * @return
     */
    ObservableList<Gril> listMyGrils();
}
