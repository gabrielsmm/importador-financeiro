package com.gabrielsmm.importadorfinanceiro.batch.processor;

import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import jakarta.validation.ValidationException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Processa as transações, validando os dados antes de serem salvos.
 */
@Component
public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao> {

    @Override
    public Transacao process(Transacao transacao) throws Exception {
        if (transacao.getCliente() == null || transacao.getCliente().isBlank()) {
            throw new ValidationException("Cliente não pode ser vazio.");
        }
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Valor deve ser maior que zero.");
        }
        return transacao;
    }

}
