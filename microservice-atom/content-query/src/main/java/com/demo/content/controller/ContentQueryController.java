package com.demo.content.controller;

import com.demo.common.dto.Result;
import com.demo.common.dto.inside.GetPlayInfoReq;
import com.demo.common.dto.inside.GetPlayInfoRsp;
import com.demo.content.dto.GetContentReq;
import com.demo.content.dto.GetContentRsp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contentCenter")
public class ContentQueryController {

    @RequestMapping(value = "/getContent", produces = { "application/json" }, method = RequestMethod.POST)
    public GetContentRsp getContent(@RequestBody final GetContentReq req) {
        System.out.println(req);
        return new GetContentRsp(new Result("1000000000", "Success"), "content from content-query");
    }
    
    @RequestMapping(value = "/getPlayInfo", produces = { "application/json" }, method = RequestMethod.POST)
    public GetPlayInfoRsp getContent(@RequestBody final GetPlayInfoReq req) {
        System.out.println(req);
        return new GetPlayInfoRsp(new Result("1000000000", "Success"), "getPlayInfo from content-query");
    }
    
}
