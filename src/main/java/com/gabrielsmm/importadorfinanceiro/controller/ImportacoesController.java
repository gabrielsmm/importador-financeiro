package com.gabrielsmm.importadorfinanceiro.controller;

import com.gabrielsmm.importadorfinanceiro.service.ImportarTransacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/importacoes")
public class ImportacoesController {

    private final ImportarTransacoesService importarTransacoesService;

    @PostMapping("/transacoes")
    public ResponseEntity<?> importarTransacoes(@RequestParam String nomeArquivo) {
        try {
            importarTransacoesService.executarImportacao(nomeArquivo);
            return ResponseEntity.ok("Importação iniciada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao iniciar a importação: " + e.getMessage());
        }
    }

}
