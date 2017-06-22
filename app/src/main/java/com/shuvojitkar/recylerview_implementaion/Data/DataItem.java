package com.shuvojitkar.recylerview_implementaion.Data;

/**
 * Created by SHOBOJIT on 6/22/2017.
 */

public class DataItem {
    String name;
    String designation;
    String phn;
    String imgUrl;

    public DataItem(String name, String designation, String phn, String imgUrl) {
        this.name = name;
        this.designation = designation;
        this.phn = phn;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhn() {
        return phn;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
