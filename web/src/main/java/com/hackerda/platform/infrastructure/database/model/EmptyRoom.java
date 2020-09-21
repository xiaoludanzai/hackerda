package com.hackerda.platform.infrastructure.database.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author Syaeldon
 */
@Data
public class EmptyRoom {
    private UrpClassroom room;

    // orderList存储该教室的空课节次
    private List<Integer> orderList = Lists.newArrayList(0,0,0,0,0);

    public EmptyRoom(UrpClassroom room) {
        this.room = room;
    }

    public void addOrder(Integer order) {
        orderList.set(order/2,1);
    }
}
