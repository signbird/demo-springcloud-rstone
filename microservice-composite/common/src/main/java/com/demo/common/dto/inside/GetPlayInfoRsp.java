package com.demo.common.dto.inside;

import com.demo.common.dto.BaseRsp;
import com.demo.common.dto.Result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetPlayInfoRsp extends BaseRsp{
    
    private static final long serialVersionUID = 1L;

    @Builder
	public GetPlayInfoRsp(Result result,String playInfo) {
		super(result);
		this.playInfo = playInfo;
	}

	/**
     * 内容的下载或者播放信息描述
     */
	private String playInfo;
}
