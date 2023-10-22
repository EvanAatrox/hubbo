package cn.hubbo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 张晓华
 * @date 2023-10-19 09:08
 * @usage 当前类的用途描述
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/current/date")
    public Date date() {
        return new Date();
    }


}
