package controllers.player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.PlayerService;
import controllers.AbstractController;
import domain.Player;
import forms.SearchPlayersForm;

@Controller
@RequestMapping("/following/player")
public class FollowingPlayerController extends AbstractController {

	@Autowired
	private PlayerService playerService;
	
	// Constructors -----------------------------------------------------------

	public FollowingPlayerController() {
		super();
	}
		
		
	//cambiar collection de player 
	@RequestMapping("/following")
	public @ResponseBody Collection<Player>  listFollowing(){
		Collection<Player>  result;
		
		try{
			result = playerService.findAllFollowingByPrincipal();
		}catch(Throwable t){
			result = new ArrayList<Player>();
		}
	
		return result;
	}
	
	//hacer un formulario para ver el "Player"
	@RequestMapping("/see")
	public @ResponseBody Player seePlayer(int playerId) {
		Player result;
				
		try{
			result = playerService.findOne(playerId);
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
		
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
	public @ResponseBody Collection<Player> searchResults(String name){
		List<Player> result;
		
		try{
			result = playerService.findAllByName(name);
		}catch(Throwable t){
			result = new ArrayList<Player>();
		}

		return result;
	}

	@RequestMapping(value = "/saveFollowing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int saveFollowing(@RequestBody String nickname) {
		int result;
		
		try{
			result = playerService.addFollowing(nickname);
		}catch(Throwable t){
			result = -1;
		}

		return result;
	}
	

}