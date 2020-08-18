package com.hackerda.platform.domain.community;

import lombok.Getter;

import java.io.Serializable;

/**
 * 图片信息
 *
 */
@Getter
public class ImageInfo implements Serializable {
	private static final long serialVersionUID = 5884756296465895791L;
	/** 图片路径 **/
	private final String path;
	/** 图片名称 **/
	private final String fileId;
	
	
	public ImageInfo(String path, String fileId) {
		this.path = path;
		this.fileId = fileId;
	}

	
}
