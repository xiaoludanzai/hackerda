package com.hackerda.platform.spider.newmodel;

import com.hackerda.platform.spider.newmodel.searchclass.PageContext;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SearchResult<T> {

    private PageContext pageContext;
    private int pageNum;
    private int pageSize;
    private List<T> records;
}
