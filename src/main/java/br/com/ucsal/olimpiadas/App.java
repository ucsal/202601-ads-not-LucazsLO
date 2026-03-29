package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;
import br.com.ucsal.olimpiadas.repository.ParticipanteRepositoryMemoria;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	private static final ParticipanteRepository participanteRepo = new ParticipanteRepositoryMemoria();
	private static final ProvaRepository provaRepo = new ProvaRepository();
	private static final QuestaoRepository questaoRepo = new QuestaoRepository();
	private static final TentativaRepository tentativaRepo = new TentativaRepository();

	private static final Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		seed();

		while (true) {
			Menu.exibirOpcoes();

			switch (in.nextLine()) {
				case "1" -> cadastrarParticipante();
				case "2" -> cadastrarProva();
				case "3" -> cadastrarQuestao();
				case "4" -> aplicarProva();
				case "5" -> listarTentativas();
				case "0" -> {
					System.out.println("tchau");
					return;
				}
				default -> System.out.println("opção inválida");
			}
		}
	}

	static void cadastrarParticipante() {
		System.out.print("Nome: ");
		var nome = in.nextLine();

		System.out.print("Email (opcional): ");
		var email = in.nextLine();

		if (nome == null || nome.isBlank()) {
			System.out.println("nome inválido");
			return;
		}

		var p = new Participante();
		p.setNome(nome);
		p.setEmail(email);

		participanteRepo.salvar(p);
		System.out.println("Participante cadastrado: " + p.getId());
	}

	static void cadastrarProva() {
		System.out.print("Título da prova: ");
		var titulo = in.nextLine();

		if (titulo == null || titulo.isBlank()) {
			System.out.println("título inválido");
			return;
		}

		var prova = new Prova();
		prova.setTitulo(titulo);

		provaRepo.salvar(prova);
		System.out.println("Prova criada: " + prova.getId());
	}

	static void cadastrarQuestao() {
		if (provaRepo.buscarTodos().isEmpty()) {
			System.out.println("não há provas cadastradas");
			return;
		}

		var provaId = escolherProva();
		if (provaId == null)
			return;

		System.out.println("Enunciado:");
		var enunciado = in.nextLine();

		var alternativas = new String[5];
		for (int i = 0; i < 5; i++) {
			char letra = (char) ('A' + i);
			System.out.print("Alternativa " + letra + ": ");
			alternativas[i] = letra + ") " + in.nextLine();
		}

		System.out.print("Alternativa correta (A–E): ");
		char correta;
		try {
			correta = QuestaoMultiplaEscolha.normalizar(in.nextLine().trim().charAt(0));
		} catch (Exception e) {
			System.out.println("alternativa inválida");
			return;
		}

		var q = new QuestaoMultiplaEscolha();
		q.setProvaId(provaId);
		q.setEnunciado(enunciado);
		q.setAlternativas(alternativas);
		q.setAlternativaCorreta(correta);

		questaoRepo.salvar(q);

		System.out.println("Questão cadastrada: " + q.getId() + " (na prova " + provaId + ")");
	}

	static void aplicarProva() {
		if (participanteRepo.buscarTodos().isEmpty()) {
			System.out.println("cadastre participantes primeiro");
			return;
		}
		if (provaRepo.buscarTodos().isEmpty()) {
			System.out.println("cadastre provas primeiro");
			return;
		}

		var participanteId = escolherParticipante();
		if (participanteId == null)
			return;

		var provaId = escolherProva();
		if (provaId == null)
			return;

		var questoesDaProva = questaoRepo.buscarTodos().stream()
				.filter(q -> q.getProvaId() == provaId).toList();

		if (questoesDaProva.isEmpty()) {
			System.out.println("esta prova não possui questões cadastradas");
			return;
		}

		var tentativa = new Tentativa();
		tentativa.setParticipanteId(participanteId);
		tentativa.setProvaId(provaId);

		System.out.println(">> Iniciando Prova...");

		for (var q : questoesDaProva) {
			System.out.println("\nQuestão #" + q.getId());
			System.out.println(q.getEnunciado());

			System.out.println("Posição inicial:");
			TabuleiroUtils.imprimirFen(q.getFenInicial());

			if (q instanceof QuestaoComAlternativas) {
				QuestaoComAlternativas separada = (QuestaoComAlternativas) q;
				for (var alt : separada.exibirAlternativas()) {
					System.out.println(alt);
				}
			}

			System.out.print("Sua resposta: ");
			String respostaDigitada = in.nextLine();

			var r = new Resposta();
			r.setQuestaoId(q.getId());
			try {
				r.setRespostaDada(respostaDigitada.trim());
			} catch (Exception ignored) {
			}
			r.setCorreta(q.isRespostaCorreta(respostaDigitada));

			tentativa.getRespostas().add(r);
		}

		tentativaRepo.salvar(tentativa);

		int nota = tentativa.calcularNota();
		System.out.println("Nota final (acertos): " + nota + " / " + tentativa.getRespostas().size());
	}



	static void listarTentativas() {
		System.out.println("\nTentativas gravadas no sistema:");
		for (var t : tentativaRepo.buscarTodos()) {
			System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
					t.getProvaId(), t.calcularNota(), t.getRespostas().size());
		}
	}

	static Long escolherParticipante() {
		System.out.println("\nParticipantes:");
		for (var p : participanteRepo.buscarTodos()) {
			System.out.printf("  %d) %s%n", p.getId(), p.getNome());
		}
		System.out.print("Escolha o id do participante: ");

		try {
			long id = Long.parseLong(in.nextLine());
			var participante = participanteRepo.buscarPorId(id);
			if (participante == null) {
				System.out.println("id inválido");
				return null;
			}
			return id;
		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}

	static Long escolherProva() {
		System.out.println("\nProvas:");
		for (var p : provaRepo.buscarTodos()) {
			System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
		}
		System.out.print("Escolha o id da prova: ");

		try {
			long id = Long.parseLong(in.nextLine());
			var prova = provaRepo.buscarPorId(id);
			if (prova == null) {
				System.out.println("id inválido");
				return null;
			}
			return id;
		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}


	static void seed() {

		var prova = new Prova();
		prova.setTitulo("Olimpíada 2026 • Nível 1 • Prova A");
		provaRepo.salvar(prova);

		var q1 = new QuestaoMultiplaEscolha();
		q1.setProvaId(prova.getId());

		q1.setEnunciado("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""");

		q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

		q1.setAlternativas(new String[] { "A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#" });

		q1.setAlternativaCorreta('C');

		questaoRepo.salvar(q1);

	}
}