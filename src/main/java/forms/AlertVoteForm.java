package forms;

import javax.validation.constraints.NotNull;

public class AlertVoteForm {

	private int alertId;
	private boolean vote;


	@NotNull
	public int getAlertId() {
		return alertId;
	}
	public void setAlertId(int alertId) {
		this.alertId = alertId;
	}
	
	@NotNull
	public boolean getVote() {
		return vote;
	}
	public void setVote(boolean vote) {
		this.vote = vote;
	}
	
}