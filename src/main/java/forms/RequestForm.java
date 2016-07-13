package forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RequestForm {

	private List<Tupla> requests;

	//Esta lista de tuplas realmente indica el id de la request en sí y el nickname del jugador que le ha invitado.
	
	@NotNull
	public List<Tupla> getRequests() {
		return requests;
	}
	public void setRequests(List<Tupla> requests) {
		this.requests = requests;
	}
	
}