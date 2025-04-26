package com.gabrielsmm.importadorfinanceiro.batch.processor;

import com.gabrielsmm.importadorfinanceiro.batch.support.TransacaoSkipListener;
import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import com.gabrielsmm.importadorfinanceiro.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Processa as transações, validando os dados antes de serem salvos.
 */
@RequiredArgsConstructor
@Component
public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao>, StepExecutionListener {

    private Set<String> transacoesExistentes;

    private final TransacaoRepository transacaoRepository;

    private final TransacaoSkipListener skipListener;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        transacoesExistentes = transacaoRepository.findAll().stream()
                .map(t -> gerarChave(t.getCliente(), t.getData(), t.getValor()))
                .collect(Collectors.toSet());
    }

    @Override
    public Transacao process(Transacao transacao) throws Exception {
        if (transacao.getCliente() == null || transacao.getCliente().isBlank()) {
            throw new ValidationException("Cliente não pode ser vazio.");
        }
        if (transacao.getData() == null || transacao.getData().isAfter(LocalDate.now())) {
            throw new ValidationException("Data não pode ser nula ou maior que a data atual.");
        }
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Valor deve ser maior que zero.");
        }

        String chave = gerarChave(transacao.getCliente(), transacao.getData(), transacao.getValor());

        if (transacoesExistentes.contains(chave)) {
            skipListener.onSkipInRead(new ValidationException(
                    "Transação já existente: Cliente=" + transacao.getCliente() +
                            ", Data=" + transacao.getData() +
                            ", Valor=" + transacao.getValor()
            ));
            return null; // ignora
        }

        return transacao;
    }

    private String gerarChave(String cliente, LocalDate data, BigDecimal valor) {
        return cliente.trim().toLowerCase() + "|" + data + "|" + valor.stripTrailingZeros();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

}
