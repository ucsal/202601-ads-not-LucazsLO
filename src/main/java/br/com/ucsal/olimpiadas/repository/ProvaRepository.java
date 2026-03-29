package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Prova;
import java.util.ArrayList;
import java.util.List;

public class ProvaRepository {
    private final List<Prova> provas = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Prova prova) {
        prova.setId(proximoId++);
        provas.add(prova);
    }

    public List<Prova> buscarTodos() {
        return provas;
    }

    public Prova buscarPorId(long id) {
        for (Prova p : provas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}