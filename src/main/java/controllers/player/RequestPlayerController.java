package controllers.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.RequestService;
import controllers.AbstractController;
import forms.RequestForm;
import forms.WaitingRoomForm;

@Controller
@RequestMapping("/request/player")
public class RequestPlayerController extends AbstractController {

	@Autowired
	private RequestService requestService;
	
	// Constructors -----------------------------------------------------------

	public RequestPlayerController() {
		super();
	}
	
	// My pending requests -----------------------------------------------------------
	
	@RequestMapping(value = "/pending", method = RequestMethod.GET)
	public @ResponseBody RequestForm pending() {
		RequestForm result;
		
		try{
			result = requestService.findAllPendingByPrincipal();
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
	
	// Accept ----------------------------------------------------------------------
	
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public @ResponseBody WaitingRoomForm accept(int requestId) {
		WaitingRoomForm result;
		
		try{
			result = requestService.accept(requestId);
		}catch(Throwable t){
			result = null;
		}

		return result;
	}
	
	// Reject -----------------------------------------------------------------------
	
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public @ResponseBody int reject(int requestId) {
		int result;
		//Devuelve 0 si se ha borrado la partida porque se ha rebajado el número mínimo de jugadores
		
		try{
			result = requestService.reject(requestId);
		}catch(Throwable t){
			result = -1;
		}

		return result;
	}
}