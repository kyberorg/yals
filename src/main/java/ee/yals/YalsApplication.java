package ee.yals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main (Start point)
 */
@SpringBootApplication
public class YalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(YalsApplication.class, args);
        telegram();
    }

    private static void telegram() {


    }
}
