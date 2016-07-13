package forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class CustomGameForm {

	private int questions;
	private List<Tupla> following;

	@Range(min=1, max=5)
	@NotNull
	public int getQuestions() {
		return questions;
	}
	public void setQuestions(int questions) {
		this.questions = questions;
	}

	@NotNull
	public List<Tupla> getFollowing() {
		return following;
	}
	public void setFollowing(List<Tupla> following) {
		this.following = following;
	}
	
}