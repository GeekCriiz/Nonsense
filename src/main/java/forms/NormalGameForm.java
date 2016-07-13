package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class NormalGameForm {

	private int questions;
	private int players;

	@Range(min=1, max=5)
	@NotNull
	public int getQuestions() {
		return questions;
	}
	public void setQuestions(int questions) {
		this.questions = questions;
	}
	
	@Range(min=3, max=8)
	@NotNull
	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}
	
}