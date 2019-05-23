package com.demo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.dto.outside.GetPlayDownloadUrlReqOut;
import com.demo.common.dto.outside.GetPlayDownloadUrlRspOut;
import com.demo.common.dto.outside.QueryContentReqOut;
import com.demo.common.dto.outside.QueryContentRspOut;
import com.demo.service.service.IContentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@RestController
@RequestMapping("/composite")
@Api(value = "内容相关接口")
public class ContentController {

    @Autowired
    private IContentService contentService;
    
    @RequestMapping(value = "/queryContent", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "查询内容", notes = "查询内容", response = QueryContentRspOut.class, tags = {
    "content" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = QueryContentRspOut.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = QueryContentRspOut.class) })
    public @ResponseBody QueryContentRspOut queryContent(@RequestBody final QueryContentReqOut req) {
        return contentService.queryContent(req);
    }
    
    @RequestMapping(value = "/getPlayDownloadUrl", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "获取播放地址", notes = "获取播放地址", response = GetPlayDownloadUrlRspOut.class, tags = {
    "content" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = GetPlayDownloadUrlRspOut.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = GetPlayDownloadUrlRspOut.class) })
    public @ResponseBody GetPlayDownloadUrlRspOut getPlayDownloadUrl(@RequestBody final GetPlayDownloadUrlReqOut req) {
    	return contentService.getPlayDownloadUrl(req);
    }
}
