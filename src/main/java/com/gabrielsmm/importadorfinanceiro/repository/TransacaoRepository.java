package com.gabrielsmm.importadorfinanceiro.repository;

import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    boolean existsByClienteAndDataAndValor(String cliente, LocalDate data, BigDecimal valor);

}
