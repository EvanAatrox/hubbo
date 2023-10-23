package cn.hubbo.security.handler;

import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.Result;
import cn.hubbo.utils.web.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-10-23 11:13
 * @usage 鉴权失败异常处理器
 */
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("访问受限", accessDeniedException);
        Result result = new Result(ResponseStatusEnum.ERROR)
                .add("detail", "访问受限制");
        ServletUtils.reposeObjectWithJson(response, result);
    }


}
