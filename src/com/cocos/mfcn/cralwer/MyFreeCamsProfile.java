package com.cocos.mfcn.cralwer;

import com.cocos.mfcn.cralwer.modals.GrilPageInfo;
import com.cocos.mfcn.models.Gril;

public interface MyFreeCamsProfile {
    String PROFILE_URL = "https://profiles.myfreecams.com/";
    /**
     * 读取用户信息
     * @param userName
     * @return
     */
    GrilPageInfo read(String userName);
}
