package com.hackerda.platform.pojo.vo;

import com.hackerda.platform.pojo.UrpClassroom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmptyRoomVo {
    private UrpClassroom urpClassroom;
    // orderList存储该教室的空课节次
    private List<Integer> orderList;
}
