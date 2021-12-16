package io.github.xiaoxpai.test.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class AppMain {
    public static void main(String[] args) {
        ServiceLoader<Log> serviceLoader =
        ServiceLoader.load(Log.class);
        Iterator<Log> iterator = serviceLoader.iterator();

        while (iterator.hasNext()) {

            Log log = iterator.next();

            log.log("JDK SPI");
            //Log4j2:JDK SPI
            //Logback:JDK SPI
        }
    }
}
