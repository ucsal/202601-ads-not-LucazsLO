package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Tentativa;
import java.util.ArrayList;
import java.util.List;

public class TentativaRepository {
    private final List<Tentativa> tentativas = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Tentativa tentativa) {
        tentativa.setId(proximoId++);
        tentativas.add(tentativa);
    }

    public List<Tentativa> buscarTodos() {
        return tentativas;
    }

    public Tentativa buscarPorId(long id) {
        for (Tentativa t : tentativas) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
}
