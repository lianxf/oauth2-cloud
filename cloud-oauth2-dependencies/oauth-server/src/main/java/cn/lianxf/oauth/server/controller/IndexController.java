package cn.lianxf.oauth.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className IndexController
 * @description 测试config server
 * @date 2019/12/19 6:43
 */
@RestController
public class IndexController {

    @Value("${common.lianxf}")
    private String lianxf;

    @RequestMapping("/lianxf")
    public String lianxf(){
        return lianxf;
    }
}
