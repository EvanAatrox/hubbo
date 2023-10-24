package cn.hubbo.web.controller;

import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 张晓华
 * @date 2023-10-19 09:08
 * @usage 当前类的用途描述
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {


    @Value("${hubbo.test}")
    private String testContent;

    @GetMapping("/current/date")
    public Date date() {
        return new Date();
    }

    @GetMapping("/config/key")
    public Result readConfig(@RequestParam String key) {
        return new Result(ResponseStatusEnum.SUCCESS).add("result", testContent);
    }


}
