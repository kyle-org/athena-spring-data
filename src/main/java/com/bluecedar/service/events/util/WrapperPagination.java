package com.bluecedar.service.events.util;

import java.util.ArrayList;
import java.util.List;

public class WrapperPagination<T> {
    private List<T> elements = new ArrayList<>();

    private int page;
    private int limit;
    private long total;

    public WrapperPagination() {
    }

    public WrapperPagination(List<T> list) {
        this.elements = list;
        this.page = 1;
        this.limit = 1;
        this.total = 1;
    }

    public WrapperPagination(List<T> list, int page, int limit, long total) {
        this.elements = list;
        if (elements != null && elements.size() > 0) {
            this.page =  page + 1;
        } else {
            // if no elements are set => set the response page to `1`
            this.page = 1;
        }
        this.limit = limit;
        this.total = total;
    }

    public List<T> getElements() {
        return this.elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public String toString() {
        return "Wrapper [ elements = " + this.elements + "].";
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
