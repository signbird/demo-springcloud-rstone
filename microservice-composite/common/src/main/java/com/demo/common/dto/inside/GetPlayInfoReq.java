package com.demo.common.dto.inside;

import com.demo.common.dto.BaseReq;
import com.demo.common.dto.Header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 本类是获取播放及下载地址请求
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayInfoReq extends BaseReq{
    
    private static final long serialVersionUID = 1L;
    
    @Builder
    public GetPlayInfoReq(Header header, String contentCode) {
        super(header);
        this.contentCode = contentCode;
    }
    

    /**
     * 内容唯一标识
     */
	private String contentCode;
}
