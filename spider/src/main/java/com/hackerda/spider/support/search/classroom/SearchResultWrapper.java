package com.hackerda.spider.support.search.classroom;

import com.hackerda.spider.support.search.SearchResult;
import lombok.Data;

@Data
public class SearchResultWrapper<T> {
    private String xqzs;
    private SearchResult<T> pageData;
}
