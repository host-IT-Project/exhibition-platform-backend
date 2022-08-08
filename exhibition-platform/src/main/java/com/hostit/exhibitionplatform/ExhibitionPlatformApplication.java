package com.hostit.exhibitionplatform;

import com.hostit.exhibitionplatform.global.config.properties.AppProperties;
import com.hostit.exhibitionplatform.global.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class ExhibitionPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExhibitionPlatformApplication.class, args);
	}

}
