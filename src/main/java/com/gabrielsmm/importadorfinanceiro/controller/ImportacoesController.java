package com.gabrielsmm.importadorfinanceiro.controller;

import com.gabrielsmm.importadorfinanceiro.service.ImportarTransacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/importacoes")
public class ImportacoesController {

    private final ImportarTransacoesService importarTransacoesService;

    @PostMapping("/transacoes")
    public ResponseEntity<?> importarTransacoes(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            importarTransacoesService.executarImportacao(arquivo);
            return ResponseEntity.ok("Arquivo enviado e importação iniciada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar o upload: " + e.getMessage());
        }
    }

}
