package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import services.QuestionService;
import controllers.AbstractController;

@Controller
@RequestMapping("/question/player")
public class QuestionPlayerController extends AbstractController {

	
	// Constructors -----------------------------------------------------------

	public QuestionPlayerController() {
		super();
	}
	
	@Autowired
	private QuestionService questionService;
	

	@RequestMapping("/selectCategory")
	public @ResponseBody int selectCategory(String category, int roundId) {
		int result;
		try{
			result = questionService.selectCategory(category, roundId);
		}catch(Throwable t){
			result = -2;
		}
		return result;
	}

}