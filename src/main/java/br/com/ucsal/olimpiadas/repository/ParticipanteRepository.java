package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Participante;
import java.util.List;

public interface ParticipanteRepository {
    void salvar(Participante participante);
    Participante buscarPorId(long id);
    List<Participante> buscarTodos();
}
