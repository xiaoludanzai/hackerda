package com.hackerda.platform.service;

import com.hackerda.platform.controller.vo.AppMessageOverviewVO;
import com.hackerda.platform.controller.vo.AppMessageVO;
import com.hackerda.platform.controller.vo.PostUserVO;
import com.hackerda.platform.domain.message.AppNoticeMessageBO;
import com.hackerda.platform.domain.message.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppMessageService {


    @Autowired
    private MessageFactory messageFactory;


    public AppMessageOverviewVO getAppNotice(String userName, Integer startId, int count) {

        List<AppNoticeMessageBO> noticeList = messageFactory.createAppNoticeByReceiver(userName, startId, count);

        List<AppMessageVO> voList = noticeList.stream().map(x -> {
            AppMessageVO messageVO = new AppMessageVO();

            messageVO.setContent(x.getContent());
            messageVO.setCreateTime(x.getCreateTime());
            messageVO.setHasRead(x.isHasRead());
            messageVO.setTagName(x.getTagName());
            messageVO.setTagType(x.getMessageTriggerSource().getCode());
            messageVO.setId(x.getId());
            messageVO.setPostId(x.getPostId());

            PostUserVO postUserVO = new PostUserVO();
            postUserVO.setAvatar(x.getSenderAvatar());
            postUserVO.setShowUserName(x.getSenderShowName());
            postUserVO.setShowUserNameOrder(0);
            postUserVO.setUserName(x.getSenderUserName());
            postUserVO.setPostAuthor(false);

            messageVO.setSender(postUserVO);

            return messageVO;
        }).sorted(Comparator.comparing(AppMessageVO::getCreateTime).reversed())
                .collect(Collectors.toList());

        long nextMaxId = voList.stream()
                .map(AppMessageVO::getId)
                .min(Long::compareUnsigned)
                .orElse(-1L);

        AppMessageOverviewVO overviewVO = new AppMessageOverviewVO();

        overviewVO.setCount(voList.size());
        overviewVO.setMessageList(voList);
        overviewVO.setNextMaxId(nextMaxId);

        return overviewVO;
    }

}
