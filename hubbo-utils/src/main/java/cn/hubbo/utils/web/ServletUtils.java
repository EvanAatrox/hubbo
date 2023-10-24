package cn.hubbo.utils.web;

import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.common.StrUtils;
import cn.hubbo.utils.lang.base.ClientInfo;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.web
 * @date 2023/10/22 21:05
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public final class ServletUtils {


    /**
     * 设置响应的数据格式和字符编码
     *
     * @param servletResponse 响应对象
     */
    private static void setCharacterEncoding(ServletResponse servletResponse) {
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setContentType(MediaType.JSON_UTF_8.toString());
    }

    /**
     * 从请求体中获取参数并封装成指定类型的对象
     *
     * @param request 请求对象
     * @param cla     目标class
     * @param <T>     期望类型
     * @return 对象
     */
    @SuppressWarnings({"rawtypes", "unchecked", "unused"})
    public static <T> T getRequiredData(ServletRequest request, Class cla) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            Gson gson = JsonUtils.getDefaultGson();
            return (T) gson.fromJson(bufferedReader, cla);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param response 响应对象
     * @param object   响应的数据
     */
    public static void reposeObjectWithJson(ServletResponse response, Object object) {
        reposeObjectWithJson(response, object, false);
    }

    /**
     * @param response            响应对象
     * @param object              响应的数据
     * @param hasIgnoreSomeFields 是否需要忽略不需要进行序列化的属性
     */
    public static void reposeObjectWithJson(ServletResponse response, Object object, boolean hasIgnoreSomeFields) {
        if (hasIgnoreSomeFields) {
            responseJsonStr(response, JsonUtils.getStrategiesGson().toJson(object));
        } else {
            responseJsonStr(response, JsonUtils.getDefaultGson().toJson(object));
        }
    }

    /**
     * 以json的格式将数据响应给客户端
     *
     * @param response 响应对象
     * @param str      响应的数据
     */
    public static void responseJsonStr(ServletResponse response, String str) {
        setCharacterEncoding(response);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.write(str);
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param request 请求
     * @return 客户端的IP地址
     */
    public static String getClientIP(ServletRequest request) {
        return request.getRemoteHost();
    }

    /**
     * 如果参数为空则返回空字符串
     *
     * @param request   请求
     * @param paramName 参数名
     * @return 获取参数值
     */
    public static String getParameterOrDefault(ServletRequest request, String paramName) {
        String value = null;
        value = request.getParameter(paramName);
        return value == null ? "" : null;
    }


    /**
     * @param request 请求对象
     * @return 客户端的相关信息，IP,请求时间,URL,平台信息等
     */
    public static ClientInfo getClientInfo(HttpServletRequest request) {
        UserAgent userAgent = UserAgentUtil.parse(StrUtils.ifNil(request.getHeader("User-Agent")));
        return new ClientInfo(request.getRemoteHost(),
                request.getRequestURL().toString(),
                StrUtils.ifNil(request.getHeader("Bearer Token")),
                new Date(), StrUtils.ifNil(request.getHeader("submitToken")));
    }


}
