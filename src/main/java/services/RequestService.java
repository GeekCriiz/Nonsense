package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.GameRepository;
import repositories.RequestRepository;
import domain.Game;
import domain.Player;
import domain.Request;
import forms.RequestForm;
import forms.Tupla;
import forms.WaitingRoomForm;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private RequestRepository requestRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private PlayerService playerService;
	@Autowired
	private GameService gameService;
	
	// Constructor -----------------------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = requestRepository.findAll();

		return result;
	}

	public Request findOne(int requestId) {
		Assert.notNull(requestId);
		Request result;

		result = requestRepository.findOne(requestId);

		return result;
	}

	public Request create(int playerId, int gameId) {
		Assert.notNull(playerId);
		Assert.notNull(gameId);
		Request result;
		
		result = new Request();
		result.setGame(gameService.findOne(gameId));
		result.setPlayer(playerService.findOne(playerId));
		
		return result;
	}

	public void delete(int id) {
		Assert.notNull(id);
		requestRepository.delete(id);
	}
	
	// Other business methods -----------------------------------------------------

	public RequestForm findAllPendingByPrincipal() {
		RequestForm result;
		Player main;
		List<Tupla> tuplas;
		Collection<Request> requests;
		Player host;
		
		main = playerService.findByPrincipal();
		result = new RequestForm();
		tuplas = new ArrayList<Tupla>();
		requests = requestRepository.findAllPendingByPrincipal(main.getId());
		
		for(Request e: requests){
			Tupla tupla = new Tupla();
			host = playerService.findHost(e.getGame().getId());
			tupla.setPlayerId(e.getId());
			tupla.setNickname(host.getNickname());
			tuplas.add(tupla);
		}
		
		result.setRequests(tuplas);
		
		return result;
	}

	public WaitingRoomForm accept(int requestId) {
		WaitingRoomForm result;
		Game game;
		Request request;
		List<Tupla> tuplas;
		List<Player> players;
		
		request = findOne(requestId);
		Assert.isTrue(request.getStatus() == "PENDING");
		game = request.getGame();
		players = new ArrayList<Player>(game.getPlayers());
		tuplas = new ArrayList<Tupla>();
		result = new WaitingRoomForm();
		
		request.setStatus("ACCEPTED");
		
		request = requestRepository.save(request);
		
		players.add(request.getPlayer());
		game.setPlayers(players);
		
		game = gameRepository.save(game);
		
		for(Player e: game.getPlayers()){
			Tupla tupla = new Tupla();
			tupla.setNickname(e.getNickname());
			tupla.setPlayerId(e.getId());
			tuplas.add(tupla);
		}
		result.setPlayers(tuplas);
		result.setHost(playerService.findHost(game.getId()).getNickname());
		result.setPlayersNumber(game.getPlayersNumber());
		result.setGameId(game.getId());
		
		if(game.getPlayersNumber() == game.getPlayers().size()){
			game.setRoundNumber(1);
			game = gameRepository.save(game);
		}
		
		return result;
	}

	public int reject(int requestId) {
		Game game;
		Request request;
		
		request = findOne(requestId);
		Assert.isTrue(request.getStatus() == "PENDING");
		game = request.getGame();
		
		request.setStatus("REJECTED");
		
		request = requestRepository.save(request);
		
		if(game.getPlayersNumber() > 3){
			game.setPlayersNumber(game.getPlayersNumber() - 1);
			game = gameRepository.save(game);
			return request.getId();
		}else{
			gameService.deleteUnstarted(game.getId());
			return 0;
		}
	}
}
