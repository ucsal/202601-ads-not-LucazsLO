package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Questao;
import java.util.ArrayList;
import java.util.List;

public class QuestaoRepository {
    private final List<Questao> questoes = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Questao questao) {
        questao.setId(proximoId++);
        questoes.add(questao);
    }

    public List<Questao> buscarTodos() {
        return questoes;
    }

    public Questao buscarPorId(long id) {
        for (Questao q : questoes) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }
}
