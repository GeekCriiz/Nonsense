package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NegativePlayerForm {
	
	//Attributes---------------------------------------
	private int playerId;
	private int gameId;
	// En la respuesta hacia Android de /negative, "gameId" será el código de la respuesta:
	// 0:		Éxito.
	// -1:		Excepción Java.
	// -100:	Hay que enviar notificación al usuario identificado por "playerId" (supera el 2 de media).
	// -200:	Hay que enviar notificación al usuario identificado por "playerId" (supera el 3 de media, aparecen en la
	// 			lista de jugadores sospechosos de hacer trampa de los administradores del sistema).
	
	@Min(0)
	@NotNull
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	 
	@NotNull
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
}
