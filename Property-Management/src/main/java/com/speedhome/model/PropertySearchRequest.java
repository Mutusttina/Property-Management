package com.speedhome.model;

public class PropertySearchRequest {

    private int catagoryId=0;
    private String searchString="";
    private String sortBy="";
    private String sortOrder="ASC";
    private int recordsPerPage=10;
    private int pageIndex=1;

    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getCatagoryId() {
        return catagoryId;
    }
    public void setCatagoryId(int catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getSortBy() {
        return sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    public int getRecordsPerPage() {
        return recordsPerPage;
    }
    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
