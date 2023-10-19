package cn.hubbo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class, DataSourceAutoConfiguration.class})
@SuppressWarnings({"all"})
public class HubboApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubboApplication.class, args);
    }


}
