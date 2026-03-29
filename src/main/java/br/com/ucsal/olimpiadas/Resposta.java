package br.com.ucsal.olimpiadas;

public class Resposta {

	private long questaoId;
	private String respostaDada;
	private boolean correta;

	public long getQuestaoId() {
		return questaoId;
	}

	public void setQuestaoId(long questaoId) {
		this.questaoId = questaoId;
	}

	public String getRespostaDada() {
		return respostaDada;
	}

	public void setRespostaDada(String respostaDada) {
		this.respostaDada = respostaDada;
	}

	public boolean isCorreta() {
		return correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}

}
