package com.hackerda.platform.domain.community;

import lombok.Getter;
import lombok.Setter;

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

	@Setter
	private RecordStatus recordStatus;

	private final String invalidPic = "https%3A%2F%2Fwx3.sinaimg.cn%2Fimages%2Fdefault_d_h_mw690.gif%23101";
	
	public ImageInfo(String path, String fileId) {
		this.path = path;
		this.fileId = fileId;
		this.recordStatus = RecordStatus.Create;
	}

	public ImageInfo(String path, String fileId, RecordStatus recordStatus) {
		this.path = path;
		this.fileId = fileId;
		this.recordStatus = recordStatus;
	}

	public String getPath(){
		if(recordStatus == RecordStatus.UnPassReview || recordStatus == RecordStatus.Delete || recordStatus == RecordStatus.UnderReview) {
			return invalidPic;
		}
		return path;
	}

	
}
