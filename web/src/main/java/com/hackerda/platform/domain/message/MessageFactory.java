package com.hackerda.platform.domain.message;

import com.hackerda.platform.domain.community.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageFactory {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;



    public List<AppNoticeMessageBO> createAppNoticeByReceiver(String userName, Integer startId, int count) {

        List<MessageBO> messageList = messageRepository.findByUserName(userName, startId, count);

        Set<String> receiver = messageList.stream().map(MessageBO::getReceiverUserName).collect(Collectors.toSet());
        Set<String> sender = messageList.stream().map(MessageBO::getSenderUserName).collect(Collectors.toSet());

        receiver.addAll(sender);

        Map<String, StudentPoster> studentPosterMap = posterRepository.findStudentPosterByUserName(receiver).stream()
                .collect(Collectors.toMap(Poster::getUserName, x -> x));

        // 先查触发点赞的消息
        List<Long> likeIdList = messageList.stream()
                .filter(x -> x.getMessageTriggerSource() == MessageTriggerSource.Like)
                .map(MessageBO::getMessageSourceId)
                .distinct()
                .collect(Collectors.toList());

        List<LikeBO> likeList = likeRepository.findByIdList(likeIdList);
        Map<Long, LikeBO> likeIdMap = likeList.stream().collect(Collectors.toMap(LikeBO::getId, x -> x));

        // 先查触发点赞的消息
        Set<Long> commentIdList = messageList.stream()
                .filter(x -> x.getMessageTriggerSource() == MessageTriggerSource.Comment)
                .map(MessageBO::getMessageSourceId)
                .collect(Collectors.toSet());

        commentIdList.addAll(likeList.stream()
                .filter(x-> x.getLikeType() == LikeType.Comment)
                .map(LikeBO::getTypeContentId)
                .collect(Collectors.toSet()));

        Map<Long, CommentBO> commentMap = commentRepository.findByIdList(new ArrayList<>(commentIdList)).stream()
                .collect(Collectors.toMap(CommentBO::getId, x -> x));

        Map<Long, PostDetailBO> postDetailMap = posterRepository.findByIdList(likeList.stream()
                .filter(x -> x.getLikeType() == LikeType.Post)
                .map(LikeBO::getTypeContentId).distinct()
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(PostDetailBO::getId, x -> x));


        return messageList.stream().map(x -> {
            AppNoticeMessageBO.AppNoticeMessageBOBuilder builder = AppNoticeMessageBO.builder()
                    .id(x.getId())
                    .receiverIdentityCategory(x.getReceiverIdentityCategory())
                    .receiverUser(studentPosterMap.get(x.getReceiverUserName()))
                    .senderIdentityCategory(x.getSenderIdentityCategory())
                    .senderUser(studentPosterMap.get(x.getSenderUserName()))
                    .messageType(x.getMessageType())
                    .messageTriggerSource(x.getMessageTriggerSource())
                    .createTime(x.getCreateTime());

            if (x.getMessageTriggerSource() == MessageTriggerSource.Like) {

                LikeBO likeBO = likeIdMap.get(x.getMessageSourceId());

                if (likeBO.getLikeType() == LikeType.Comment) {
                    CommentBO commentBO = commentMap.get(likeBO.getTypeContentId());
                    builder.sourceComment(commentBO);
                    builder.postId(commentBO.getPostId());
                    builder.sourceLike(likeBO);
                }

                if (likeBO.getLikeType() == LikeType.Post) {
                    PostDetailBO postDetailBO = postDetailMap.get(likeBO.getTypeContentId());
                    builder.sourcePost(postDetailBO);
                    builder.postId(postDetailBO.getId());
                    builder.sourceLike(likeBO);
                }
            }

            if (x.getMessageTriggerSource() == MessageTriggerSource.Comment) {
                CommentBO commentBO = commentMap.get(x.getMessageSourceId());
                builder.sourceComment(commentBO);
                builder.postId(commentBO.getPostId());
            }

            return builder.build();

        }).collect(Collectors.toList());

    }
}
