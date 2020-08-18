package com.hackerda.platform.infrastructure.community;

import com.hackerda.platform.domain.community.ContentSecurityCheckService;
import org.springframework.stereotype.Service;

@Service
public class ContentSecurityCheckServiceImpl implements ContentSecurityCheckService {

    @Override
    public boolean checkMsg(String content) {
        return false;
    }
}
