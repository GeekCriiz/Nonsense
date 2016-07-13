package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.AnswerPlayerService;
import services.QuestionService;
import controllers.AbstractController;
import forms.AnswerPlayerForm;

@Controller
@RequestMapping("/answerPlayer/player")
public class AnswerPlayerPlayerController extends AbstractController {

	@Autowired
	private AnswerPlayerService answerPlayerService;
	
	@Autowired
	private QuestionService questionService;
	
	// Constructors -----------------------------------------------------------

	public AnswerPlayerPlayerController() {
		super();
	}	
	
	@RequestMapping(value = "/answer")
	public @ResponseBody AnswerPlayerForm answer(int playerId, int gameId, int questionId) {
		AnswerPlayerForm result;
		
		try{
			result = new AnswerPlayerForm();
			result.setPlayerId(playerId);
			result.setGameId(gameId);
			result.setQuestionId(questionId);
		}catch(Throwable t){
			result= null;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/answer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int answer(@RequestBody AnswerPlayerForm answerPlayerForm) {
		int result;
		
		try{
			result = answerPlayerService.answer(answerPlayerForm);

		}catch(Throwable t){
			result= -1;
		}
		
		return result;
	}
	
	@RequestMapping("/choosePerfect")
	public @ResponseBody int choosePerfect(int answerPlayerId, int roundId) {
		int result;
		try{
			result = questionService.choosePerfect(answerPlayerId, roundId);
		}catch(Throwable t){
			result = -2;
		}
		return result;
	}
	
}