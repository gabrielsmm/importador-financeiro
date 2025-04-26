package com.gabrielsmm.importadorfinanceiro.batch.config;

import com.gabrielsmm.importadorfinanceiro.batch.support.TransacaoSkipPolicy;
import com.gabrielsmm.importadorfinanceiro.batch.support.TransacaoSkipListener;
import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuração do job de importação de transações.
 * Este job é responsável por ler, processar e gravar as transações no banco de dados.
 */
@RequiredArgsConstructor
@Configuration
public class ImportarTransacoesConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ItemReader<Transacao> transacaoReader;
    private final ItemProcessor<Transacao, Transacao> transacaoProcessor;
    private final ItemWriter<Transacao> transacaoWriter;
    private final TransacaoSkipListener skipListener;

    @Bean
    public Job importarTransacoesJob() {
        return new JobBuilder("importarTransacoesJob", jobRepository)
                .start(importarTransacoesStep())
                .build();
    }

    @Bean
    public Step importarTransacoesStep() {
        return new StepBuilder("importarTransacoesStep", jobRepository)
                .<Transacao, Transacao>chunk(1000, transactionManager)
                .reader(transacaoReader)
                .processor(transacaoProcessor)
                .writer(transacaoWriter)
                .faultTolerant()
                .skipPolicy(new TransacaoSkipPolicy(skipListener))
                .listener(skipListener)
                .build();
    }

}
