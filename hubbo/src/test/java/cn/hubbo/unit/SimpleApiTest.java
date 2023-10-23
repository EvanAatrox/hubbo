package cn.hubbo.unit;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.unit
 * @date 2023/10/22 21:22
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public class SimpleApiTest {


    @Test
    public void testSpringMediaTypeAPI() {
        MediaType mediaType = MediaType.valueOf("application/json");
        System.out.println(mediaType);
        System.out.println(mediaType.includes(MediaType.APPLICATION_FORM_URLENCODED));
    }

    @Test
    public void testCalendarAPI() {
        Instant instant = Calendar.getInstance().toInstant();
        System.out.println(instant.getEpochSecond());
    }

    @Test
    public void testCalendarAPI2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
        Date date = simpleDateFormat.parse("2023-10-24 10:12:03");
        System.out.println(date.after(new Date()));
    }

    @Test
    public void testParseUserAgent() {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36";
        UserAgent agent = UserAgentUtil.parse(userAgent);
        System.out.println(agent);
        Browser browser = agent.getBrowser();
        System.out.println(browser);
        System.out.println(browser.getName());

    }


}
