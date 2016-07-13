package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AlertRepository;
import domain.Alert;
import domain.Game;
import domain.Player;

@Service
@Transactional
public class AlertService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AlertRepository alertRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	
	// Constructor -----------------------------------------------------

	public AlertService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	// Enviar alerta (como un save)
	public int sendAlert(int gameId, int playerId) {
		Game game;
		Player player;
		Alert alert;
		Alert result;
			
		game = gameService.findOne(gameId);
		player = playerService.findOne(playerId);
		alert = new Alert();
			
		alert.setGame(game);
		alert.setPlayer(player);
		alert.setTimer(new Date(System.currentTimeMillis()));
		alert.setVoted(0);
		alert.setYes(0);
		
		result = alertRepository.save(alert);
		
		return result.getId();
	}
	
	public Collection<Alert> findAll() {
		Collection<Alert> result;

		result = alertRepository.findAll();

		return result;
	}

	public Alert findOne(int alertId) {
		Assert.notNull(alertId);
		Alert result;

		result = alertRepository.findOne(alertId);

		return result;
	}
	
	public void delete(int id) {
		Assert.notNull(id);
		alertRepository.delete(id);
	}

	// Other business methods -----------------------------------------------------
	
	public int alertVote(int alertId, boolean vote) {
		Alert alert;
		Game game;
		Alert result;
		
		// Comprobar que hay 3 o más jugadores en la partida
		alert = findOne(alertId);
		game = alert.getGame();
		
		Assert.isTrue(gameService.findPlayers(game.getId()).size() > 2);
		Assert.isTrue(game.getPlayersNumber() > 2);
		
		// Comprobar que ha pasado un día desde que se envió la notificación
		Date date = new Date(System.currentTimeMillis());
		date.setDate(date.getDate()-1);
		
		Assert.isTrue(alert.getTimer().before(date));
		
		// Votar
		alert.setVoted(alert.getVoted()+1);
		
		if(vote){
			alert.setYes(alert.getYes()+1);
		}
		
		result = alertRepository.save(alert);
		
		if((alert.getVoted() + 1 == game.getPlayersNumber()) && (alert.getVoted() == alert.getYes())){
			gameService.kickPlayer(game, alert.getPlayer());
		}
		
		return result.getId();
	}

}
