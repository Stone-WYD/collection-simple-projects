
package com.jgdsun.ba.brand.baorui.model.devicetype;

 
import com.google.gson.annotations.Expose;

  
@SuppressWarnings("unused")
public class BrDeviceType {

    @Expose
    private Object backgroundUrl;
    @Expose
    private Long categoryId;
    @Expose
    private Object categoryName;
    @Expose
    private String createBy;
    @Expose
    private String createTime;
    @Expose
    private Long id;
    @Expose
    private Long isShow;
    @Expose
    private String lastUpdateTime;
    @Expose
    private String pagePath;
    @Expose
    private String projectId;
    @Expose
    private Object sort;
    @Expose
    private String subsystemName;

    public Object getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(Object backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Object getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Object categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsShow() {
        return isShow;
    }

    public void setIsShow(Long isShow) {
        this.isShow = isShow;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

}
