package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PlayerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Game;
import domain.Player;
import domain.Round;
import domain.Score;
import forms.EditionPlayerForm;
import forms.NegativePlayerForm;
import forms.RegisterPlayerForm;
import forms.SeePlayerForm;
import forms.Tupla;

@Service
@Transactional
public class PlayerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PlayerRepository playerRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private GameService gameService;
	@Autowired
	private RoundService roundService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private AdminService adminService;

	// Constructor -----------------------------------------------------

	public PlayerService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Player> findAll() {
		Collection<Player> result;

		result = playerRepository.findAll();

		return result;
	}

	public Player findOne(int playerId) {
		Assert.notNull(playerId);
		Player result;

		result = playerRepository.findOne(playerId);

		return result;
	}

	// Other business methods -----------------------------------------------------

	public Player create() {
		Player result;		
	    UserAccount userAccount;	
	    Collection<Player> following;
	    Collection<Player> followers;
	    Collection<Game> games;
	   
		userAccount = userAccountService.createPlayer();	
		following = new ArrayList<Player>();
		followers = new ArrayList<Player>();
		games = new ArrayList<Game>();
		
		result = new Player();
				
		result.setUserAccount(userAccount);
		result.setFollowing(following);
		result.setFollowers(followers);
		result.setGames(games);
		
		Assert.notNull(result);
						
		return result;
	}
	
	public int save(Player player){
		String password;
		Md5PasswordEncoder encoder;
		UserAccount userAccount;	
	
		if(player.getId() == 0) {
			userAccount = player.getUserAccount();
			encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(userAccount.getPassword(), null);
			
			player.getUserAccount().setPassword(password);
			
			player.setAbandoned(0);
			player.setAverageScore(0);
			player.setBans(0);
			player.setBlocked(false);
			player.setFavouriteCat("");
			player.setWon(0);
			player.setPlayed(0);
			player.setNegatives(0);

		}

		Assert.notNull(player);	  
		
		//playerRepository.save(player);
		
		return playerRepository.save(player).getId();
		
	}
	
	public Player reconstruct(RegisterPlayerForm playerForm) {
		Player result;
		UserAccount userAccount;

		Assert.notNull(playerForm);
		//La contraseña tiene que ser igual a la repetida
		Assert.isTrue(playerForm.getPassword().equals(playerForm.getRepeatPassword()));
		
		result = create();
			
		userAccount = result.getUserAccount();
		userAccount.setUsername(playerForm.getUsername());
		userAccount.setPassword(playerForm.getPassword());
		
		result.setName(playerForm.getName());
		result.setSurname(playerForm.getSurname());
		result.setEmail(playerForm.getEmail());
		result.setNickname(playerForm.getNickname());
		result.setUserAccount(userAccount);
		
		
		return result;
		
	}
	
	public RegisterPlayerForm fragment(Player player) {
		RegisterPlayerForm result;
		
		Assert.notNull(player);
		
		result = new RegisterPlayerForm();
		
		result.setName(player.getName());
		result.setSurname(player.getSurname());
		result.setEmail(player.getEmail());
		result.setNickname(player.getNickname());
		
		result.setUsername(player.getUserAccount().getUsername());
		//Esto es porque si el player edita su perfil no le tiene que salir
		//la contraseña encriptada con Md5
		if(player.getId() == 0) {
			result.setPassword(player.getUserAccount().getPassword());
		}
		
		return result;
		
	}
	
	public void checkPlayer(Player player) {
		UserAccount principal;
		UserAccount owner;
		   
		principal = LoginService.getPrincipal();   
		owner = player.getUserAccount();
		
		Assert.notNull(principal);		   
		Assert.isTrue(principal.equals(owner));
	}
	

	public Integer savePlayer(Player player) {
		Assert.notNull(player);
		Player result;

		result = playerRepository.save(player);

		return result.getId();
	}

	public Player findByPrincipal() {
		
		Player result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = playerRepository.findByUserAccount(userAccount);
		
		return result;
	}
	
	//Solo se pueden editar los atributos aquí indicados
	public EditionPlayerForm fragmentToEdit(Player player){
		Assert.notNull(player);
		EditionPlayerForm result;
		
		result = new EditionPlayerForm();
		
		result.setId(player.getId());
		result.setName(player.getName());
		result.setEmail(player.getEmail());
		result.setPassword(player.getUserAccount().getPassword());
		result.setSurname(player.getSurname());
		result.setNickname(player.getNickname());
		
		return result;
	}
	
	public Player reconstructToEdit(EditionPlayerForm editionUserForm){
		Player result;
		UserAccount userAccount;
		Player principal;
		Md5PasswordEncoder encoder;
		String password;
		
		principal = findByPrincipal();
		
		Assert.notNull(editionUserForm);
		//La contraseña repetida es la misma
		Assert.isTrue(editionUserForm.getPassword().equals(editionUserForm.getRepeatPassword()));

		result = findOne(editionUserForm.getId());
		//Se modifica a sí mismo
		Assert.isTrue(principal.equals(result));
		
		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(editionUserForm.getPassword(), null);
		
		userAccount = result.getUserAccount();
		result.setName(editionUserForm.getName());
		result.setEmail(editionUserForm.getEmail());
		result.setNickname(editionUserForm.getNickname());
		result.setSurname(editionUserForm.getSurname());
		
		if(!password.equals(userAccount.getPassword())) {
			userAccount.setPassword(password);
		}
		
		result.setUserAccount(userAccount);
		
		return result;
	}
	

	public List<Player> findAllByName(String name) {
		Assert.notNull(name);
		Player principal;
		List<Player> result;
		
		principal = findByPrincipal();
		result = playerRepository.findAllByName(name);
		
		if(result.contains(principal)){
			result.remove(principal);
		}
		
		return result;
	}

	public Integer addFollowing(String nickname) {
		Player main = findByPrincipal();
		List<Player> following = new ArrayList<Player>(main.getFollowing());
		Player player = playerRepository.findByNickname(nickname);
		
		following.add(player);
		
		main.setFollowing(following);
		
		playerRepository.save(main);
		
		return main.getId();
	}

	public Collection<String> findAllBlockedByNickname() {
		Collection<String> result;

		result = playerRepository.findAllBlockedByNickname();

		return result;
	}
	
	public Collection<Player> findAllWithBans() {
		Collection<Player> result;

		result = playerRepository.findAllWithBans();

		return result;
	}

	public Collection<Player> findAllFollowingByPrincipal() {
		Collection<Player> result;
		Player player;
		int playerId;
		
		player = findByPrincipal();		
		playerId = player.getId();

		result = playerRepository.findAllFollowingByPrincipal(playerId);

		return result;
	}

	//Expulsar un jugador
	public void banPlayer (int playerId){
		//Comprobar que está logueado como admin
		Admin admin;
		
		admin = adminService.findByPrincipal();
		Assert.notNull(admin);	
			
		/*Será a los administradores del sistema a quienes les aparecerá un listado con los jugadores tramposos cuando estos lleguen a los
		3 negativos de media por partida, y de aquí podrán acceder a sus perfiles para evaluar si expulsarlos. 
		 */
				
		Player player;
		int bans;
		Date currentDate;
		Calendar calendar;
			
		player = findOne(playerId);
			
		//El contador de bans aumenta en uno cada vez que se expulse al jugador
		bans = player.getBans()+1;		
		calendar = Calendar.getInstance();
		currentDate = new Date(System.currentTimeMillis());
		calendar.setTime(currentDate);
		
		//Los negativos de un jugador se reinician cuando este es expulsado
		player.setNegatives(0);
			
		//1 ban -> 1 día. Expulsión leve
		if(bans==1){
			calendar.add(Calendar.DATE,1);
		}
			
		//2 ban -> 1 semana. Expulsión moderada
		else if(bans==2){
			calendar.add(Calendar.DATE,7);
		}
			
		//3 ban -> 1 mes. Expulsión grave
		else if(bans==3){
			calendar.add(Calendar.DATE,30);
		}
			
		/* 4 ban -> Indefinido. Expulsión muy grave.En este caso el jugador no podrá acceder nunca más a su
		cuenta, ésta quedará bloqueada. Sólo los administradores podrán ver esta cuenta y será eliminada del sistema a los 2 meses.
		Cuando lleve 4 bans, la cuenta es bloqueada.*/
		else if(bans==4){
			player.setBlocked(true);	
		}
		
		player.setBanExpirationDate(calendar.getTime());
	}


	public Collection<String> findAllByNickname() {
		Collection<String> result;

		result = playerRepository.findAllByNickname();

		return result;
	}

	public Collection<String> findAllWithBansByNickname() {
		Collection<String> result;

		result = playerRepository.findAllWithBansByNickname();

		return result;
	}

	public NegativePlayerForm putNegative(NegativePlayerForm negativePlayerForm) {
		// Comprobar que el usuario que pone el negativo es el logueado y es speaker de la ronda actual (que aún no ha acabado)
		Player speaker = findByPrincipal();
		Game current = gameService.findOne(negativePlayerForm.getGameId());
		Round round = roundService.findOneByGame(current.getId(), current.getRoundNumber());
		Assert.isTrue(round.getSpeaker().equals(speaker) && !round.isEnded());
		
		Player player;
		int played;
		int negatives;
		double average;
		NegativePlayerForm result;
		
		result = new NegativePlayerForm();
		player = findOne(negativePlayerForm.getPlayerId());
		negatives = player.getNegatives();
		played = player.getPlayed();
		average = 0;
		
		if(negatives != 0 && played != 0){
			average = negatives/played;
		}
		
		player.setNegatives(negatives+1);
		
		player = playerRepository.save(player);
		
		if((played >= 10) && (average < 2) && (player.getNegatives() >= 2)){
			// Si ha jugado el número de partidas mínimas y su media de negativos por partidas supera 2
			result.setGameId(-100);
		}else if((played >= 10) && (average < 3) && (player.getNegatives() >= 3)){
			// Si ha jugado el número de partidas mínimas y su media de negativos por partidas supera 3
			result.setGameId(-200);
		}else{
			result.setGameId(0);
		}
		
		result.setPlayerId(player.getId());
		
		return result;
	}
	
	public Collection<String> findAllCheaters() {
		Collection<String> result;

		result = playerRepository.findAllCheaters();

		return result;
	}
	
	public List<Tupla> findAllFollowingIdByPrincipal(){
		List<Tupla> result;
		Player player;
		List<Player> following;
		
		player = findByPrincipal();		

		result = new ArrayList<Tupla>();
		following = new ArrayList<Player>(player.getFollowing());
		
		for(Player e: following){
			Tupla tupla = new Tupla();
			tupla.setPlayerId(e.getId());
			tupla.setNickname(e.getNickname());
			result.add(tupla);
		}
		
		return result;
	}

	public Player findHost(int gameId) {
		Player host;
		
		host = playerRepository.findHost(gameId);
		
		return host;
	}

	public Player findByAnswer(int answerPlayerId) {
		Player result;
		
		result = playerRepository.findByAnswer(answerPlayerId);
		
		return result;
	}

	public boolean isSpeaker(int playerId, int gameId) {
		boolean result;
		Game game;
		Player speaker;
		
		result = false;
		game = gameService.findOne(gameId);
		speaker = playerRepository.findSpeaker(gameId, game.getRoundNumber());
		
		if(findByPrincipal().equals(speaker)){
			result = true;
		}
		
		return result;
	}

	public Player findSpeaker(int gameId) {
		Game game;
		Player speaker;
		
		game = gameService.findOne(gameId);
		speaker = playerRepository.findSpeaker(gameId, game.getRoundNumber());
		
		return speaker;
	}
	
	//Actualizar perfiles según fin de la partida
	public void updateProfiles(int gameId){
		Game game;
		List<Score> best;
		List<Player> winners;

		game = gameService.findOne(gameId);
		best = scoreService.findBest(gameId);
		winners = new ArrayList<Player>();
		
		for(Score e: best){
			winners.add(e.getPlayer());
		}

		for(Score e: game.getScores()){
			Player player;
			String favouriteCat;
			int definition;
			int character_;
			int film;
			int acronym;
			int date;
			
			player = e.getPlayer();
			
			if(winners.contains(player)){// Actualizar partidas ganadas al/los ganador/es de la partida
				player.setWon(player.getWon() + 1);
			}
			player.setPlayed(player.getPlayed() + 1);// Actualizar número de partidas jugadas
			
			definition = playerRepository.findPlayerDefinition(e.getId());
			character_ = playerRepository.findPlayerCharacter_(e.getId());
			film = playerRepository.findPlayerFilm(e.getId());
			acronym = playerRepository.findPlayerAcronym(e.getId());
			date = playerRepository.findPlayerDate(e.getId());
						
			if((definition >= character_) && (definition >= acronym) && (definition >= film) && (definition >= date)){
				favouriteCat = "DEFINITION";
			}else if((character_ >= definition) && (character_ >= acronym) && (character_ >= film) && (character_ >= date)){
				favouriteCat = "CHARACTER_";
			}else if((acronym >= character_) && (acronym >= definition) && (acronym >= film) && (acronym >= date)){
				favouriteCat = "ACRONYM";
			}else if((film >= character_) && (film >= acronym) && (film >= definition) && (film >= date)){
				favouriteCat = "FILM";
			}else if((date >= character_) && (date >= acronym) && (date >= definition) && (date >= definition)){
				favouriteCat = "DATE";
			}else{
				favouriteCat = "?";
			}
			
			player.setFavouriteCat(favouriteCat);// Actualizar categoría favorita (en la que tiene más puntos)
			
			player.setAverageScore(((definition + character_ + film + acronym + date)*1.0)/(player.getPlayed()*1.0));// Actualizar puntuación media por partida
			
			playerRepository.save(player);
		}
	}
	
	public SeePlayerForm seePlayer(int playerId) {
		Assert.notNull(playerId);
		SeePlayerForm result;
		Player player;
		
		player = findOne(playerId);

		result = new SeePlayerForm();
		
		result.setName(player.getName());
		result.setSurname(player.getSurname());
		result.setNickname(player.getNickname());

		return result;
	}

	public Collection<String> findAllFollowers() {
		Collection<String> result;
		Player player;
		
		player = findByPrincipal();
		result = playerRepository.findAllFollowers(player.getId());
		
		return result;
	}
	
	public Collection<String> findAllFollowing() {
		Collection<String> result;
		Player player;
		
		player = findByPrincipal();
		result = playerRepository.findAllFollowing(player.getId());
		
		return result;
	}

}
