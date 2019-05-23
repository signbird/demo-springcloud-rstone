package com.demo.common.dto.outside;

import com.demo.common.dto.BaseRsp;
import com.demo.common.dto.Result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是获取播放及下载地址响应
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayDownloadUrlRspOut extends BaseRsp {

    private static final long serialVersionUID = 1L;

    @Builder
	public GetPlayDownloadUrlRspOut(Result result, String downloadUrl) {
		super(result);
		this.downloadUrl = downloadUrl;
	}
    
    private String downloadUrl;
}
