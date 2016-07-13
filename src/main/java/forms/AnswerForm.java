package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class AnswerForm {
	
	//Attributes---------------------------------------
	private int playerId;
	private String nickname;
	private String answer;
	private int miniScore;
	
	@Min(0)
	@NotNull
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	 
	@NotBlank
	@SafeHtml
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Min(0)
	public int getMiniScore() {
		return miniScore;
	}
	public void setMiniScore(int miniScore) {
		this.miniScore = miniScore;
	}
	
	@NotBlank
	@SafeHtml
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
