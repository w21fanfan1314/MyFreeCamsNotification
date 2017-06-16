package com.cocos.mfcn.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class Gril {
    // 唯一标识
    private int id;
    // 头像地址
    private String imageUrl;
    // 名字
    private String name;
    // 状态
    private GrilState state;

    public Gril(String name) {
        this.name = name;
    }

    public Gril() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GrilState getState() {
        return state;
    }

    public void setState(GrilState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.getName() +"("+this.state+")";
    }
}
