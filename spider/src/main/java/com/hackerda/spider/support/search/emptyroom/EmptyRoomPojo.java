package com.hackerda.spider.support.search.emptyroom;

import lombok.Data;

import java.util.List;

/**
 * @author Syaeldon
 */
@Data
public class EmptyRoomPojo {
    private EmptyRoomPageContext pageContext;
    private int pageNum;
    private int pageSize;
    private List<EmptyRoomRecord> records;
}
