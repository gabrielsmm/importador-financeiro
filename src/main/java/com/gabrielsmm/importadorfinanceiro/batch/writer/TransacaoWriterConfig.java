package com.gabrielsmm.importadorfinanceiro.batch.writer;

import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import com.gabrielsmm.importadorfinanceiro.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura o writer para o job do Spring Batch.
 * Este writer é responsável por persistir as transações processadas
 * no banco de dados utilizando o TransacaoRepository.
 */
@RequiredArgsConstructor
@Configuration
public class TransacaoWriterConfig {

    private final TransacaoRepository repository;

    @Bean
    public ItemWriter<Transacao> transacaoWriter() {
        return repository::saveAll;
    }

}
