package com.gabrielsmm.importadorfinanceiro.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportarTransacoesService {

    private final JobLauncher jobLauncher;
    private final Job importarTransacoesJob;

    public ImportarTransacoesService(JobLauncher jobLauncher, @Qualifier("importarTransacoesJob") Job importarTransacoesJob) {
        this.jobLauncher = jobLauncher;
        this.importarTransacoesJob = importarTransacoesJob;
    }

    public void executarImportacao(String nomeArquivo) throws Exception {
        JobParameters parametros = new JobParametersBuilder()
                .addString("arquivo", nomeArquivo)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(importarTransacoesJob, parametros);
    }

}
