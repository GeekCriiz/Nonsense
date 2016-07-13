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

import services.GameService;
import services.PlayerService;
import controllers.AbstractController;
import forms.ContinueGameForm;
import forms.CustomGameForm;
import forms.NormalGameForm;
import forms.SeeGameForm;

@Controller
@RequestMapping("/game/player")
public class GamePlayerController extends AbstractController {

	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;

	
	// Constructors -----------------------------------------------------------

	public GamePlayerController() {
		super();
	}
	

	//Listing todas las de un player
	@RequestMapping("/games")
	public @ResponseBody Collection<SeeGameForm>  listGames(){
		Collection<SeeGameForm>  result;
		
		try{
			result = gameService.findAllGamesByPrincipal();
		}catch(Throwable t){
			result = new ArrayList<SeeGameForm>();
		}
		
		return result;
	}
	
	//Listing pending accepted
	@RequestMapping("/gamesPending")
	public @ResponseBody Collection<SeeGameForm> listGamesPending(){
		Collection<SeeGameForm>  result;
			
		try{
			result = gameService.findPendingGamesByPrincipal();
		}catch(Throwable t){
			result = new ArrayList<SeeGameForm>();
		}
			
		return result;
	}
	
	//Listing en curso
	@RequestMapping("/gamesEnCurso")
	public @ResponseBody Collection<SeeGameForm> listGamesEnCurso(){
		Collection<SeeGameForm>  result;
				
		try{
			result = gameService.findEnCursoGamesByPrincipal();
		}catch(Throwable t){
			result = new ArrayList<SeeGameForm>();
		}
				
		return result;
	}

	//Displaying
	@RequestMapping("/see")
	public @ResponseBody SeeGameForm seeGame(int gameId) {
		SeeGameForm result;
		
		try{
			result =gameService.seeGame(gameId);
		}catch(Throwable t){
			result = null;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/newNormalGame", method = RequestMethod.GET)
	public @ResponseBody NormalGameForm newNormalGame(){
		NormalGameForm result;
		
		try{
			result = new NormalGameForm();
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
	
	@RequestMapping(value = "/newNormalGame", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Integer> newNormalGame(@RequestBody NormalGameForm normalGameForm) {
		List<Integer> result;
		//Lista de dos valores, de los cuales el primero (el segundo es el id del juego creado o 0 en caso de error):
		//-1: Excepción Java
		//1: Unido a nuevo juego
		//2: Unido a nuevo juego y lista de jugadores completa (empieza la partida)
		//3: Nuevo juego creado
		
		try{
			result = gameService.searchNormal(normalGameForm.getQuestions(),normalGameForm.getPlayers());
		}catch(Throwable t){
			result = new ArrayList<Integer>();
			result.add(-1);
			result.add(0);
		}

		return result;
	}
			
	@RequestMapping(value = "/newCustomGame", method = RequestMethod.GET)
	public @ResponseBody CustomGameForm newCustomGame(){
		CustomGameForm result;
		
		try{
			result = new CustomGameForm();
			result.setFollowing(playerService.findAllFollowingIdByPrincipal());
		}catch(Throwable t){
			result = null;
		}
		
		return result;

	}
	
	@RequestMapping(value = "/newCustomGame", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int newCustomGame(@RequestBody CustomGameForm customGameForm) {
		int result;
		
		try{
			result = gameService.createCustom(customGameForm.getQuestions(),customGameForm.getFollowing());
		}catch(Throwable t){
			result= -1;
		}
		
		return result;
	}
	
	@RequestMapping("/continueGame")
	public @ResponseBody ContinueGameForm continueGame(int gameId) {
		ContinueGameForm result;
		try{
			result = gameService.continueGame(gameId);
		}catch(Throwable t){
			result = null;
		}
		return result;
	}
}