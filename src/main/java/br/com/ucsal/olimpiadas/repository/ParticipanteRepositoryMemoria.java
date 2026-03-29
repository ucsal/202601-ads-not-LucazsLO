package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Participante;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteRepositoryMemoria implements ParticipanteRepository {
    private final List<Participante> participantes = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Participante participante) {
        participante.setId(proximoId++);
        participantes.add(participante);
    }

    public List<Participante> buscarTodos() {
        return participantes;
    }

    public Participante buscarPorId(long id) {
        for (Participante p : participantes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}