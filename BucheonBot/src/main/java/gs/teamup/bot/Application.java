package gs.teamup.bot;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@CommonsLog
@EnableScheduling
@SpringBootApplication
@ImportResource("classpath*:/applicationContext.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("start~");
    }
}
