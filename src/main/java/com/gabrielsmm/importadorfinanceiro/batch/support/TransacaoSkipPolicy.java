package com.gabrielsmm.importadorfinanceiro.batch.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.validator.ValidationException;

public class TransacaoSkipPolicy implements SkipPolicy {

    private static final Logger log = LoggerFactory.getLogger(TransacaoSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (t instanceof FlatFileParseException ffpe) {
            log.warn("Linha inválida ignorada: {}. Erro: {}", ffpe.getInput(), ffpe.getMessage());
            return true; // Ignora a linha inválida
        }
        if (t instanceof ValidationException ve) {
            log.warn("Erro de validação ignorado: {}", ve.getMessage());
            return true; // Ignora a linha com erros de validação
        }
        return false; // Não ignora outros tipos de exceções
    }

}
