package com.backend.tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TccApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
			System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(TccApplication.class, args);
	}

	@Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    @Bean
    public Cloudinary cloudinary(Dotenv dotenv) {
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        return cloudinary;
    }

}
