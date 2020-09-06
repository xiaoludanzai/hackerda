package com.hackerda.platform.domain.community;


public interface ContentSecurityCheckService {

    boolean isSecurityContent(String content);

    boolean isSecurityImage(byte[] image);
}
