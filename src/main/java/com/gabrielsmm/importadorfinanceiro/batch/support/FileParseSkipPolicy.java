package com.gabrielsmm.importadorfinanceiro.batch.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

public class FileParseSkipPolicy implements SkipPolicy {

    private static final Logger log = LoggerFactory.getLogger(FileParseSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if (t instanceof FlatFileParseException ffpe) {
            log.warn("Linha inválida ignorada: {}. Erro: {}", ffpe.getInput(), ffpe.getMessage());
            return true; // Ignora a linha inválida
        }
        return false; // Não ignora outros tipos de exceções
    }

}
