package com.gabrielsmm.importadorfinanceiro.batch.reader;

import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.time.ZoneId;

/**
 * Configuração do leitor de arquivo CSV para o Spring Batch.
 * Este leitor lê os dados do arquivo CSV e os mapeia para objetos Transacao.
 */
@Configuration
public class TransacaoReaderConfig {

    @StepScope
    @Bean
    public FlatFileItemReader<Transacao> transacaoReader(@Value("#{jobParameters['arquivo']}") String caminhoArquivo) {
        return new FlatFileItemReaderBuilder<Transacao>()
                .name("transacaoReader")
                .resource(new FileSystemResource(caminhoArquivo))
                .delimited()
                .names("id", "cliente", "data", "valor", "moeda", "categoria")
                .fieldSetMapper(fieldSet -> {
                    Transacao t = new Transacao();
                    t.setId(fieldSet.readLong("id"));
                    t.setCliente(fieldSet.readString("cliente"));
                    t.setData(fieldSet.readDate("data", "yyyy-MM-dd").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    t.setValor(fieldSet.readBigDecimal("valor"));
                    t.setMoeda(fieldSet.readString("moeda"));
                    t.setCategoria(fieldSet.readString("categoria"));
                    return t;
                })
                .linesToSkip(1) // Pula o cabeçalho
                .build();
    }

}
