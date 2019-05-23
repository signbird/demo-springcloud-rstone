package com.demo.common.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.common.client.fallback.ContentQueryFallback;
import com.demo.common.dto.inside.GetContentReq;
import com.demo.common.dto.inside.GetContentRsp;
import com.demo.common.dto.inside.GetPlayInfoReq;
import com.demo.common.dto.inside.GetPlayInfoRsp;

@FeignClient(name = "content-query", fallbackFactory = ContentQueryFallback.class)
@Qualifier("contentQueryClient")
public interface ContentQueryClient {

    @RequestMapping(value = "/contentCenter/getContent", produces = { "application/json" }, method = RequestMethod.POST)
    GetContentRsp getContent(@RequestBody GetContentReq req);

    @RequestMapping(value = "/contentCenter/getPlayInfo", produces = {"application/json" }, method = RequestMethod.POST)
    GetPlayInfoRsp getPlayInfo(@RequestBody GetPlayInfoReq req);
}
