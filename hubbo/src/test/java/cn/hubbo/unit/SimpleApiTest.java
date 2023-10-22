package cn.hubbo.unit;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.Calendar;

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


}
