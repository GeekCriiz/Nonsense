package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.PlayerService;
import domain.Player;
import forms.RegisterPlayerForm;

@Controller
@RequestMapping("/player")
public class PlayerController extends AbstractController{
	
	//Services
	@Autowired
	private PlayerService playerService;
	

	//Constructors
	public PlayerController(){
		super();		
	}
	
	//Create
	@RequestMapping("/createPlayer")
	public @ResponseBody RegisterPlayerForm createPlayer() {
		RegisterPlayerForm result;
		Player player;
		
		try{
			player = playerService.create();
			result = playerService.fragment(player);
		}catch(Throwable t){
			result = null;
		}
		
		return result;
	}
		
	// Edition-------------------------------------------------
		
	@RequestMapping(value = "/savePlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int save(@RequestBody RegisterPlayerForm playerForm){
		int result;
		Player player;

		try{
				player = playerService.reconstruct(playerForm);
				result = playerService.save(player);
		} catch (org.springframework.dao.DataIntegrityViolationException oops) {
		       result = -50;	
		}catch(Throwable t){
			result = -1;
		}
		
		return result;
	}
	
}
