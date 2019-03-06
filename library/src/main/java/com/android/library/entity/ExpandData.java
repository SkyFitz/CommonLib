package com.android.library.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ExpandData extends AbstractExpandableItem<ExpandData> implements MultiItemEntity {

    private int level;
    private int itemType;
    private String id;
    private String title;
    private String url;
    private String desc;
    private double cooperatorId;
    private double groupId;
    private boolean isRefresh;
    private boolean isMore;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public double getCooperatorId() {
        return cooperatorId;
    }

    public void setCooperatorId(double cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    public double getGroupId() {
        return groupId;
    }

    public void setGroupId(double groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
