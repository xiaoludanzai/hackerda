package com.hackerda.spider.support.search;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SearchResultDateWrapper<T> {
    private int status;
    private String msg;
    private SearchResult<T> data;
}
