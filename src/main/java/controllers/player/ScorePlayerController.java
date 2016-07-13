package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ScoreService;
import controllers.AbstractController;
import forms.GameRankingForm;

@Controller
@RequestMapping("/score/player")
public class ScorePlayerController extends AbstractController {

	@Autowired
	private ScoreService scoreService;
	
	// Constructors -----------------------------------------------------------

	public ScorePlayerController() {
		super();
	}
	
	// See game current ranking ---------------------------------------------------------------------
	
	@RequestMapping(value = "/gameRanking", method = RequestMethod.GET)
	public @ResponseBody GameRankingForm gameRanking(int gameId) {
		GameRankingForm result;
		
		try{
			result = scoreService.getGameRanking(gameId);
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
}