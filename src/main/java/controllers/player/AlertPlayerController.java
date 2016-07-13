package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.AlertService;
import controllers.AbstractController;
import forms.AlertForm;
import forms.AlertVoteForm;

@Controller
@RequestMapping("/alert/player")
public class AlertPlayerController extends AbstractController {

	@Autowired
	private AlertService alertService;
	
	// Constructors -----------------------------------------------------------

	public AlertPlayerController() {
		super();
	}

	// Teóricamente esto tiene que devolver un JSON con el Alert creado
	@RequestMapping(value = "/alert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int alert(@RequestBody AlertForm alertForm) {
		int result;
		
		try{
			result = alertService.sendAlert(alertForm.getGameId(), alertForm.getPlayerId());;
		}catch(Throwable t){
			result= -1;
		}
		return result;
	}
	
	@RequestMapping(value = "/alertVote", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int alertVote(@RequestBody AlertVoteForm alertVoteForm) {
		int result;
		
		try{
			result = alertService.alertVote(alertVoteForm.getAlertId(), alertVoteForm.getVote());;
		}catch(Throwable t){
			result= -1;
		}
		return result;
	}
	
}