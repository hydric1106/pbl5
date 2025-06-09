// package com.pbl5.pbl5;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class Pbl5Application {

// 	public static void main(String[] args) {
// 		SpringApplication.run(Pbl5Application.class, args);
// 	}

// }
package com.pbl5.pbl5;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Pbl5Application {

    public static void main(String[] args) {
        SpringApplication.run(Pbl5Application.class, args);
    }
     
    @Bean
    public ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            String port = environment.getProperty("server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");
            
            System.out.println("\n========================================================");
            System.out.println("Web da khoi dong thanh cong!");
            System.out.println("Nhap vao lien ket sau de truy cap web:");
            System.out.println("http://localhost:" + port + contextPath);
            System.out.println("========================================================\n");
        };
    }
} 