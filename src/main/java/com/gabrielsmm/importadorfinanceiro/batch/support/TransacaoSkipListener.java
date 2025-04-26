package com.gabrielsmm.importadorfinanceiro.batch.support;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransacaoSkipListener implements SkipListener<Object, Object> {

    private final List<String> linhasComErro = new ArrayList<>();

    @Override
    public void onSkipInRead(Throwable t) {
        if (t instanceof FlatFileParseException ffpe) {
            linhasComErro.add("Linha " + ffpe.getLineNumber() + ": " + ffpe.getInput());
        }
    }

    public List<String> getLinhasComErro() {
        return linhasComErro;
    }

    public void limpar() {
        linhasComErro.clear();
    }

}
