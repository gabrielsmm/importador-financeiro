package com.gabrielsmm.importadorfinanceiro.controller;

import com.gabrielsmm.importadorfinanceiro.service.ImportarTransacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/importacoes")
public class ImportacoesController {

    private final ImportarTransacoesService importarTransacoesService;

    @PostMapping("/transacoes")
    public ResponseEntity<?> importarTransacoes(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            List<String> erros = importarTransacoesService.executarImportacao(arquivo);

            if (erros.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensagem", "Arquivo enviado e importação iniciada com sucesso."));
            } else {
                Map<String, Object> resposta = new HashMap<>();
                resposta.put("mensagem", "Importação concluída com erros.");
                resposta.put("erros", erros);
                return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(resposta);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("mensagem", "Erro ao processar o upload: " + e.getMessage()));
        }
    }

}
