package org.wyd;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyApplication.class, args);
        FxApplication.setSpringContext(context);
        Application.launch(FxApplication.class, args);
    }

}
