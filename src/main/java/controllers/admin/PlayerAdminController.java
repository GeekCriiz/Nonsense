package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.PlayerService;
import controllers.AbstractController;
import domain.Player;
import forms.SearchPlayersForm;
import forms.SeePlayerForm;

@Controller
@RequestMapping("/player/admin")
public class PlayerAdminController extends AbstractController{
	
	//Services
	@Autowired
	private PlayerService playerService;

	//Constructors
	public PlayerAdminController(){
		super();		
	}
	
	//Listing
	@RequestMapping("/players")
	public @ResponseBody Collection<String>  listPlayer(){
		Collection<String>  result;
		
		try{
			result = playerService.findAllByNickname();
		}catch(Throwable t){
			result = new ArrayList<String>();
		}
		
		return result;
	}

		
	//Listing blockedPlayers	
	@RequestMapping("/playersBlocked")
	public @ResponseBody Collection<String>  listPlayerBlocked(){
		Collection<String>  result;
		
		try{
			result = playerService.findAllBlockedByNickname();
		}catch(Throwable t){
			result = new ArrayList<String>();
		}
		
		return result;
	}
	
	//Listing bansPlayers		
	@RequestMapping("/playersBans")
	public @ResponseBody Collection<String>  listPlayerBans(){
		Collection<String>  result;
		
		try{
			result = playerService.findAllWithBansByNickname();
		}catch(Throwable t){
			result = new ArrayList<String>();
		}
		
		return result;
	}
	
	//Displaying
	@RequestMapping("/see")
	public @ResponseBody SeePlayerForm seePlayer(int playerId) {
		SeePlayerForm result;
				
		try{
			result =playerService.seePlayer(playerId);
		}catch(Throwable t){
			result = null;
		}
					
		return result;
	}
		
	//Searching
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody SearchPlayersForm search(){
		SearchPlayersForm result;
		
		try{
			result = new SearchPlayersForm();
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
	
	@RequestMapping(value = "/searchResults", method = RequestMethod.GET)
	public @ResponseBody List<Player> searchResults(String name){
		List<Player> result;
		
		try{
			result = playerService.findAllByName(name);
		}catch(Throwable t){
			result = new ArrayList<Player>();
		}

		return result;
	}

	//BanPlayer
	@RequestMapping(value="/banPlayer", method=RequestMethod.GET)
	public @ResponseBody int cancel(@RequestBody int playerId) {
		int result;
		
		try{
			playerService.banPlayer(playerId);
			result = 0;
		} catch (Throwable oops) {
			result= -1;
		}

		return result;
	}
}
