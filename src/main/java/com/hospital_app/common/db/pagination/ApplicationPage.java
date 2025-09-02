package com.hospital_app.common.db.pagination;

import java.util.List;

public class ApplicationPage<T> {

    int pageNumber;
    int pageSize;
    int totalPages;
    long totalElements;
    boolean isLast;
    boolean isFirst;
    List<T> content;

    public ApplicationPage(int pageNumber, int pageSize, int totalPages, long totalElements, boolean isLast, boolean isFirst, List<T> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.isLast = isLast;
        this.isFirst = isFirst;
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public List<T> getContent() {
        return content;
    }
}
