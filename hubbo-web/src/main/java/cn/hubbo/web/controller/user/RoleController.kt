package cn.hubbo.web.controller.user

import cn.hubbo.domain.enumeration.ResponseStatusEnum
import cn.hubbo.domain.vo.Result
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Package cn.hubbo.web.controller.user
 * @author 张晓华
 * @date 2023/11/1 22:18
 * @version V1.0
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */

@RestController
@RequestMapping("/role")
class RoleController {


    fun role(): Result {
        return Result(ResponseStatusEnum.SUCCESS).add("desc", "Kotlin测试");
    }


}