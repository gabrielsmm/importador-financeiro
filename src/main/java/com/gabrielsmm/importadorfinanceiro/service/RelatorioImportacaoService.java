package com.gabrielsmm.importadorfinanceiro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioImportacaoService {

    public File gerarRelatorio(List<String> erros) throws IOException {
        if (erros.isEmpty()) {
            return null; // Retorna null se não houver erros
        }

        File relatorio = new File("relatorio_importacao.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relatorio))) {
            writer.write("Relatório de Importação\n");
            writer.write("========================\n\n");
            for (String erro : erros) {
                writer.write(erro);
                writer.newLine();
            }
        }
        return relatorio;
    }

}
