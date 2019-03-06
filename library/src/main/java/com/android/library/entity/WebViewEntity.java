package com.android.library.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class WebViewEntity implements Parcelable {
    private boolean showTitle = true;
    private String title;
    private String Url;

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.showTitle ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.Url);
    }

    public WebViewEntity() {
    }

    protected WebViewEntity(Parcel in) {
        this.showTitle = in.readByte() != 0;
        this.title = in.readString();
        this.Url = in.readString();
    }

    public static final Creator<WebViewEntity> CREATOR = new Creator<WebViewEntity>() {
        @Override
        public WebViewEntity createFromParcel(Parcel source) {
            return new WebViewEntity(source);
        }

        @Override
        public WebViewEntity[] newArray(int size) {
            return new WebViewEntity[size];
        }
    };
}