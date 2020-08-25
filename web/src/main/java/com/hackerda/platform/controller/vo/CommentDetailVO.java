package com.hackerda.platform.controller.vo;

import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class CommentDetailVO {


    private int commentCount;

    private List<Overview> commentList;


    public CommentDetailVO() {

    }

    public CommentDetailVO(List<CommentVO> commentDetailList) {

        Map<Long, List<CommentVO>> collect = commentDetailList.stream().collect(Collectors.groupingBy(CommentVO::getRootCommentId,
                Collectors.mapping(x -> x, Collectors.toList())));

        List<Overview> overviewList = collect.values().stream().map(value -> {
            value.sort(Comparator.comparing(CommentVO::getPostTime));

            Overview overview = new Overview();
            overview.setRoot(value.get(0));
            if (value.size() > 1) {
                overview.setReplyList(value.subList(1, value.size()));
            } else {
                overview.setReplyList(Collections.emptyList());
            }

            return overview;
        })
                .sorted(Comparator.comparing(x -> x.getRoot().getPostTime()))
                .collect(Collectors.toList());

        this.commentList = overviewList;
        this.commentCount = overviewList.size();

    }


    @Data
    public static class Overview {
        private CommentVO root;

        private List<CommentVO> replyList;

    }
}
