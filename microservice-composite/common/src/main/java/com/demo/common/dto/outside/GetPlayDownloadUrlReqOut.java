package com.demo.common.dto.outside;

import javax.validation.constraints.NotEmpty;

import com.demo.common.dto.BaseReq;
import com.demo.common.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 类描述: 本类是获取播放及下载地址外部请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayDownloadUrlReqOut extends BaseReq {

    private static final long serialVersionUID = 1L;
    
    @Builder
    public GetPlayDownloadUrlReqOut(Header header,String userId, String contentCode, String contentType) {
		super(header);
		this.userId = userId;
		this.contentCode = contentCode;
		this.contentType = contentType;
	}

	/**
     * 用户ID
     */
    private String userId;

    /**
     * 内容唯一标识
     */
    @NotEmpty(message = "内容标识不能为空")
    private String contentCode;

    /**
     * 作品类型
     */
    private String contentType;

}
