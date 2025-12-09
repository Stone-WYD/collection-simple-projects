package com.jgdsun;

import com.jgdsun.qms.service.Ims4HandlerService;
import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.scheduling.annotation.EnableScheduling;

/**
 * @author Stone
 * @since 2025-10-16
 */
@EnableScheduling
public class App {


    public static void main(String[] args) {
        Solon.start(App.class, args);
    }
}
