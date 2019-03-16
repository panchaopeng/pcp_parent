package com.pcp.qa.client;

import com.pcp.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @depict: 调用pcp-base服务
 * @author: PCP
 * @create: 2019-03-12 17:08
 */
@Component
@FeignClient(value = "pcp-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    Result findById(@PathVariable("labelId") String labelId);
}
