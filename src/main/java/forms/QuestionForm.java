package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class QuestionForm {
	
	//Attributes---------------------------------------
	private String description;
	private String category;
	
	@SafeHtml
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@SafeHtml
	@Pattern(regexp = "^DEFINITION|CHARACTER_|DATE|FILM|ACRONYM$")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
