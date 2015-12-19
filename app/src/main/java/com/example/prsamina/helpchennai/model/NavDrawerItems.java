package com.example.prsamina.helpchennai.model;

/**
 * Created by prsamina on 12/19/2015.
 */
public class NavDrawerItems {
    private String title;
    private int icon;
    public NavDrawerItems(){}
    public NavDrawerItems(String title,int icon)
    {
        this.icon=icon;
        this.title=title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
