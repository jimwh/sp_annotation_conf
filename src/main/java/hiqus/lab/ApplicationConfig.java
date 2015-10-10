package hiqus.lab;

import hiqus.lab.db.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"hiqus.lab"})
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    public static void main(String[] args) throws Exception {

        log.info("application start...");
        ApplicationContext ctx = SpringApplication.run(ApplicationConfig.class, args);
        String[] defName = ctx.getBeanDefinitionNames();
        for (String name : defName) {
            log.info("beanDefinitionName={}", name);
        }
        Busboy busboy = ctx.getBean(Busboy.class);
        busboy.test();

        SpringApplication.exit(ctx);
    }

}