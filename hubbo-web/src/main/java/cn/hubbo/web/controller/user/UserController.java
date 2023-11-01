package cn.hubbo.web.controller.user;

import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.web.controller.user
 * @date 2023/11/1 22:18
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @PostMapping
    public Result addUser() {
        return new Result(ResponseStatusEnum.SUCCESS);
    }


}
