package com.gabrielsmm.importadorfinanceiro.repository;

import com.gabrielsmm.importadorfinanceiro.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
