package com.pcp.qa.client.impl;

import com.pcp.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @depict: 熔断类
 * @author: PCP
 * @create: 2019-03-14 20:09
 */
@Component
public class BaseClientImpl implements BaseClient {

    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.OK,"熔断器启动了");
    }
}
