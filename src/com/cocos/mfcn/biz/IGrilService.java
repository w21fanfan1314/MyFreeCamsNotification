package com.cocos.mfcn.biz;

import com.cocos.mfcn.models.Gril;
import javafx.collections.ObservableList;

public interface IGrilService {
    /**
     * 查询
     * @return
     */
    ObservableList<Gril> listGrils(int page, int max);

    /**
     * 详情
     * @param id
     * @return
     */
    Gril grilDetail(int id);
}
