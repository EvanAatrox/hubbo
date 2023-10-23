package cn.hubbo.web.controller;

import cn.hubbo.utils.lang.base.ClientInfo;
import cn.hubbo.utils.web.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.web.controller
 * @date 2023/10/23 21:23
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@RestController
@RequestMapping("/resource")
public class ProtectedResourceController {


    @GetMapping("/client/ip")
    public ClientInfo clientInfo(HttpServletRequest request) {
        return ServletUtils.getClientInfo(request);
    }


}
