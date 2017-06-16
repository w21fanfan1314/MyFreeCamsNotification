package com.cocos.mfcn.cralwer.impls;


import com.cocos.mfcn.cralwer.MyFreeCamsProfile;
import com.cocos.mfcn.cralwer.modals.GrilPageInfo;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyFreeCamsProfileImpl implements MyFreeCamsProfile {
    public static final String[] PROFILE_SECTIONS;

    static {
        PROFILE_SECTIONS = new String[]{
                "username",
                "cam_score",
                "gender",
                "weidgt",
                "height",
                "age",
                "city",
                "sexual_preference",
                "marital_status",
                "pets",
                "about_me"
        };
    }

    private Document doc;
    private String currentUrl;


    @Override
    public GrilPageInfo read(String userName) {
        this.currentUrl = PROFILE_URL + userName;
//        try {
//            Map<String,String> headers = new HashMap<>();
//            headers.put("Cache-Control", "no-cache");
//            headers.put("Pragma", "no-cache");
//            this.doc = Jsoup.connect(currentUrl)
//                    .ignoreContentType(true)
//                    .ignoreHttpErrors(true)
//                    .headers(headers)
//                    .timeout(1000 * 60 * 2)
//                    .get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        final WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        try {
            final HtmlPage htmlPage = webClient.getPage(this.currentUrl);
            this.doc = Jsoup.parse(htmlPage.asXml());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != this.doc)
        {
            System.out.println(doc.html());
            GrilPageInfo grilPageInfo = new GrilPageInfo();
            grilPageInfo.setAvatar(this.profileAvatar());
            grilPageInfo.setMainPhoto(this.mainPhoto());
            grilPageInfo.setStatus(this.status());
            grilPageInfo.setUserName(this.profileSection(PROFILE_SECTIONS[0]));
            grilPageInfo.setCamScore(this.profileSection(PROFILE_SECTIONS[1]));
            grilPageInfo.setGender(this.profileSection(PROFILE_SECTIONS[2]));
            grilPageInfo.setWeight(this.profileSection(PROFILE_SECTIONS[3]));
            grilPageInfo.setHeight(this.profileSection(PROFILE_SECTIONS[4]));
            grilPageInfo.setAge(this.toInt(this.profileSection(PROFILE_SECTIONS[5])));
            grilPageInfo.setCity(this.profileSection(PROFILE_SECTIONS[6]));
            grilPageInfo.setSexualPreference(this.profileSection(PROFILE_SECTIONS[7]));
            grilPageInfo.setMaritalStatus(this.profileSection(PROFILE_SECTIONS[8]));
            grilPageInfo.setPets(this.profileSection(PROFILE_SECTIONS[9]));
            grilPageInfo.setAboutMe(this.profileSection(PROFILE_SECTIONS[10]));
            return grilPageInfo;
        }
        return null;
    }

    /**
     * 头像
     * @return
     */
    private String profileAvatar()
    {
        if (null != doc)
        {
            Element element = doc.getElementById("profile_avatar");
            if (null != element)
                return element.attr("src");
        }
        return null;
    }

    private String mainPhoto()
    {
        if (null != doc)
        {
            Element element = doc.getElementById("main_photo");
            if (null != element)
                return element.attr("src");
        }
        return null;
    }

    private String status()
    {
//        Element element = doc.getElementById("member_status_value");
//        if (null != element)
//            return element.ownText();
//        return null;
        Elements elements = doc.select("span#member_status_value > a");
        if (null != elements && 0 < elements.size())
            return elements.get(0).html();
        return null;
    }

    private String profileSection(String label)
    {
        if (null != doc)
        {
            String value = label + "_value";
            Element element = doc.getElementById(value);
            if (null != element)
                return element.text();
        }
        return null;
    }

    private int toInt(String text)
    {
        if (null != text && !text.isEmpty())
        {
            return Integer.valueOf(text);
        }
        return 0;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public static void main(String args[])
    {
        MyFreeCamsProfileImpl camsProfile = new MyFreeCamsProfileImpl();
        GrilPageInfo grilPageInfo = camsProfile.read("Sunhiee");
        System.out.println(camsProfile.getCurrentUrl());
        System.out.println("Saria_K profile section :" + grilPageInfo);

    }
}
