package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class Tripla {

	private int current;
	private int last;
	private String nickname;

	@Min(0)
	@NotNull
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
		
	@Min(0)
	@NotNull
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
		
	@SafeHtml
	@NotBlank
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}