package io.github.xiaoxpai.test.spi.impl;

import io.github.xiaoxpai.test.spi.Log;

public class Logback implements Log {
    @Override
    public void log(String info) {
        System.out.println("Logback:" + info);
    }
}
