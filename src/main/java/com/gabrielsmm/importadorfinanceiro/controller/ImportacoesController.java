package com.gabrielsmm.importadorfinanceiro.controller;

import com.gabrielsmm.importadorfinanceiro.service.ImportarTransacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/importacoes")
public class ImportacoesController {

    private final ImportarTransacoesService importarTransacoesService;

    @PostMapping("/transacoes")
    public ResponseEntity<?> importarTransacoes(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            File relatorio = importarTransacoesService.executarImportacao(arquivo);

            if (relatorio == null) {
                return ResponseEntity.ok(Map.of("mensagem", "Arquivo enviado e importação realizada com sucesso."));
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_importacao.txt");
                return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                        .headers(headers)
                        .body(new FileSystemResource(relatorio));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("mensagem", "Erro ao processar o upload: " + e.getMessage()));
        }
    }

}
