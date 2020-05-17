package com.hackerda.platform.spider.newmodel.searchclassroom;

import com.hackerda.platform.spider.newmodel.SearchResult;
import lombok.Data;

@Data
public class SearchResultWrapper<T> {
    private String xqzs;
    private SearchResult<T> pageData;
}
