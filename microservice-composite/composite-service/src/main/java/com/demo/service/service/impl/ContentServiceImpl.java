package com.demo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.common.client.ContentQueryClient;
import com.demo.common.dto.inside.GetContentReq;
import com.demo.common.dto.inside.GetContentRsp;
import com.demo.common.dto.inside.GetPlayInfoReq;
import com.demo.common.dto.inside.GetPlayInfoRsp;
import com.demo.common.dto.outside.GetPlayDownloadUrlReqOut;
import com.demo.common.dto.outside.GetPlayDownloadUrlRspOut;
import com.demo.common.dto.outside.QueryContentReqOut;
import com.demo.common.dto.outside.QueryContentRspOut;
import com.demo.service.dto.DemoConfig;
import com.demo.service.service.IContentService;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements IContentService {

	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private DemoConfig config;
	
    @Qualifier("contentQueryClient")
    @Autowired
    private ContentQueryClient contentQueryClient;

    @Override
    public QueryContentRspOut queryContent(QueryContentReqOut req) {
        System.out.println("before client invoke getContent...config=" + config.getTest());
        
        GetContentReq contentReq = new GetContentReq(req.getRequestHeader(), req.getContentCode());
        GetContentRsp contentRsp = contentQueryClient.getContent(contentReq);
        return new QueryContentRspOut(contentRsp.getResult(), contentRsp.getContent());
    }

    @Override
    public GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req) {
        System.out.println("before client invoke getPlayDownloadUrl...");
        GetPlayInfoReq innerReq = new GetPlayInfoReq(req.getRequestHeader(), req.getContentCode());
        GetPlayInfoRsp rsp = restTemplate.postForObject("http://content-query/contentCenter/getPlayInfo", innerReq, GetPlayInfoRsp.class, "");
    	
//        GetPlayInfoRsp rsp = contentQueryClient.getPlayInfo(innerReq);
        return new GetPlayDownloadUrlRspOut(rsp.getResult(), rsp.getPlayInfo());
    }
}
