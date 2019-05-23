package com.demo.service.service;

import com.demo.common.dto.outside.GetPlayDownloadUrlReqOut;
import com.demo.common.dto.outside.GetPlayDownloadUrlRspOut;
import com.demo.common.dto.outside.QueryContentReqOut;
import com.demo.common.dto.outside.QueryContentRspOut;

public interface IContentService {

    QueryContentRspOut queryContent(QueryContentReqOut req);
    
    GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req);
}
