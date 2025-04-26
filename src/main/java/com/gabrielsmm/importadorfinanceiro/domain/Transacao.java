package com.gabrielsmm.importadorfinanceiro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    private Long id;
    private String cliente;
    private LocalDate data;
    private BigDecimal valor;
    private String moeda;
    private String categoria;

}
