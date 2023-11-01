package cn.hubbo.web.controller.system

import cn.hubbo.common.domain.to.SecurityUser
import cn.hubbo.utils.common.annotation.kotlin.SL4J
import cn.hubbo.utils.common.annotation.kotlin.SL4J.Companion.log
import cn.hubbo.utils.common.base.ClientInfo
import cn.hubbo.utils.web.ServletUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * @Package cn.hubbo.web.controller.system
 * @author 张晓华
 * @date 2023/11/1 22:24
 * @version V1.0
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


@RestController
@RequestMapping("/resource")
@SL4J
open class ProtectedResourceController {

    @GetMapping("/client/ip")
    @PreAuthorize("hasAuthority('dev.*.*')")
    fun clientInfo(request: HttpServletRequest?): ClientInfo {
        var principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is SecurityUser) {
            // smartCast
            log.info("访问当前资源的用户信息 {}", principal.userDetail)
        }
        return ServletUtils.getClientInfo(request)
    }


}