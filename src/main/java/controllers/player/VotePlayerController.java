package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.VoteService;
import controllers.AbstractController;
import forms.VoteForm;

@Controller
@RequestMapping("/vote/player")
public class VotePlayerController extends AbstractController {

	@Autowired
	private VoteService voteService;
	
	// Constructors -----------------------------------------------------------

	public VotePlayerController() {
		super();
	}
	
	// Vote ---------------------------------------------------------------------
	
	@RequestMapping(value = "/vote", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int vote(@RequestBody VoteForm voteForm) {
		int result;
		
		try{
			result = voteService.vote(voteForm.getAnswerPlayerId(), voteForm.getGameId());
		}catch(Throwable t){
			result = -1;
		}

		return result;
	}

}