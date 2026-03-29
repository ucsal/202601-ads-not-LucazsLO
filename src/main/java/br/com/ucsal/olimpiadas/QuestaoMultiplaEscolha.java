package br.com.ucsal.olimpiadas;

import java.util.Arrays;

public class QuestaoMultiplaEscolha extends Questao {

    private String[] alternativas = new String[5];
    private char alternativaCorreta;

    public void setAlternativas(String[] alternativas) {
        if (alternativas == null || alternativas.length != 5) {
            throw new IllegalArgumentException("Alternativas invalidas");
        }
        this.alternativas = Arrays.copyOf(alternativas, 5);
    }

    public void setAlternativaCorreta(char correta) {
        this.alternativaCorreta = normalizar(correta);
    }

    public static char normalizar(char c) {
        char up = Character.toUpperCase(c);
        if (up < 'A' || up > 'E') {
            throw new IllegalArgumentException("Alternativa invalida");
        }
        return up;
    }

    @Override
    public String[] exibirAlternativas() {
        return alternativas;
    }

    @Override
    public boolean isRespostaCorreta(String resposta) {
        try {
            char marcada = resposta.trim().charAt(0);
            return normalizar(marcada) == alternativaCorreta;
        } catch (Exception e) {
            return false;
        }
    }
}
