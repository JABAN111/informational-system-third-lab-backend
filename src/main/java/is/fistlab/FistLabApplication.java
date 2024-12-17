package is.fistlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FistLabApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FistLabApplication.class, args);
    }

}
