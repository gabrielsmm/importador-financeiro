package com.gabrielsmm.importadorfinanceiro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ImportarTransacoesService {

    private final JobLauncher jobLauncher;
    private final Job importarTransacoesJob;

    public ImportarTransacoesService(JobLauncher jobLauncher, @Qualifier("importarTransacoesJob") Job importarTransacoesJob) {
        this.jobLauncher = jobLauncher;
        this.importarTransacoesJob = importarTransacoesJob;
    }

    public void executarImportacao(MultipartFile arquivo) throws Exception {
        String nomeOriginal = arquivo.getOriginalFilename();
        if (nomeOriginal == null || !nomeOriginal.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Apenas arquivos .csv são permitidos.");
        }

        log.info("Iniciando importação do arquivo: {}", nomeOriginal);
        Path destino = salvarArquivoTemporariamente(arquivo);

        JobParameters parametros = new JobParametersBuilder()
                .addString("arquivo", destino.toAbsolutePath().toString())
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(importarTransacoesJob, parametros);

        log.info("Status da execução: {}", execution.getStatus());
        log.info("Finalizado em: {}", execution.getEndTime());
    }

    private static Path salvarArquivoTemporariamente(MultipartFile arquivo) throws IOException {
        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path destino = Paths.get(System.getProperty("java.io.tmpdir")).resolve(nomeArquivo);
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        return destino;
    }

}
