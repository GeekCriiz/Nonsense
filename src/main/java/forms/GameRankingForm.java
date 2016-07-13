package forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class GameRankingForm {

	private List<Tripla> scores;

	@NotNull
	public List<Tripla> getScores() {
		return scores;
	}
	public void setScores(List<Tripla> scores) {
		this.scores = scores;
	}
	
}