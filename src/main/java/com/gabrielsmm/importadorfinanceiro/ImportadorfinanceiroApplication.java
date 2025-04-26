package com.gabrielsmm.importadorfinanceiro;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ImportadorfinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportadorfinanceiroApplication.class, args);
	}

}
