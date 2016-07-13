package controllers.player;

import java.util.ArrayList;
import java.util.Collection;

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
import forms.EditionPlayerForm;
import forms.NegativePlayerForm;
import forms.SeePlayerForm;

@Controller
@RequestMapping("/player/player")
public class PlayerPlayerController extends AbstractController{

	// Constructors -----------------------------------------------------------
	public PlayerPlayerController(){
		super();
	}

	// Services ---------------------------------------------------------------
	@Autowired
	private PlayerService playerService;

	//Listing followers
	@RequestMapping("/listFollowers")
	public @ResponseBody Collection<String> listFollowers(){
		Collection<String>  result;

		try{
			result = playerService.findAllFollowers();
		}catch(Throwable t){
			result = new ArrayList<String>();
		}
				
		return result;
	}

	//Listing following
	@RequestMapping("/listFollowing")
	public @ResponseBody Collection<String> listFollowing(){
		Collection<String>  result;

		try{
			result = playerService.findAllFollowing();
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
	

	// Edition -----------------------------------------------------------------
	@RequestMapping("/editAdmin")
	public @ResponseBody EditionPlayerForm editPlayer(@RequestBody int playerId) {
		EditionPlayerForm result;
		Player player;
			
		try{
			player = playerService.findOne(playerId);
			result = playerService.fragmentToEdit(player);
		}catch(Throwable t){
			result = null;
		}
				
		return result;
	}

	@RequestMapping(value = "/savePlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int save(@RequestBody EditionPlayerForm playerForm){
		int result;
		Player player;

		try{
			player = playerService.reconstructToEdit(playerForm);
			result = playerService.save(player);
		} catch (org.springframework.dao.DataIntegrityViolationException oops) {
		    result = -50;
		}catch(Throwable t){
			result = -1;
		}

		return result;
	}
	
	// Negative -------------------------------------------------

	@RequestMapping(value = "/negative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NegativePlayerForm negative(@RequestBody NegativePlayerForm negativePlayerForm){
		NegativePlayerForm result;
		
		try{
			// - Devuelve -100 si hay que mandar una alerta al jugador de que le están marcando como sospechoso de hacer trampa
			// y que si continúa así podrá ser expulsado del juego temporalmente. 
			// - Devuelve -200 si hay que mandar una alerta al jugador de que se ha comunicado a los administradores del sistema
			// que le han marcado demasiadas veces como sospechoso de hacer trampa.
			// - En otro caso, devuelve el ID del jugador.
			result = playerService.putNegative(negativePlayerForm);
		}catch(Throwable t){
			result = new NegativePlayerForm();
			result.setPlayerId(0);
			result.setGameId(-1);
		}
		
		return result;
	}
}
