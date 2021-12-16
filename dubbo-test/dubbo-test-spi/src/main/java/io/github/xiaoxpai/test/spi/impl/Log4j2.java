package io.github.xiaoxpai.test.spi.impl;

import io.github.xiaoxpai.test.spi.Log;

public class Log4j2 implements Log {
    @Override

    public void log(String info) {

        System.out.println("Log4j2:" + info);

    }
}
