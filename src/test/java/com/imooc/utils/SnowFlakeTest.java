package com.imooc.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zhang Pengnian
 * @since 2019-10-14 17:11
 */

@SpringBootTest
public class SnowFlakeTest {

    @Test
    public void snowFlakeTest() {
        SnowFlake snowFlake = new SnowFlake();
        System.out.println(snowFlake.nextId());
        System.out.println(snowFlake.nextId());
    }

}
