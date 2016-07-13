/* LoginService.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import domain.Actor;
import domain.Player;

@Service
@Transactional
public class LoginService implements UserDetailsService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	ActorService actorService;
	
	// Business methods -------------------------------------------------------

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Assert.notNull(username);
		
		//Añadido para los bans	
		Actor actor;
		Player player;
		Date currentDate;
		//fin añadido
		
		UserDetails result;
		
		actor = actorService.findByUsername(username);

		result = userRepository.findByUsername(username);
		
		//Añadido para los bans
		currentDate = new Date(System.currentTimeMillis());

		if(actor!=null){
			if(actor instanceof Player){
				player = (Player) actor;
				if(player.isBlocked()){
					result = null;
				}
				else if(player.getBanExpirationDate().compareTo(currentDate)>0){
					result = null;
				}
			}
		}
		//fin añadido
		
		Assert.notNull(result);		
		// WARNING: The following sentences prevent lazy initialisation problems!
		Assert.notNull(result.getAuthorities());
		result.getAuthorities().size();

		return result;
	}

	public static UserAccount getPrincipal() {
		UserAccount result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof UserAccount);
		result = (UserAccount) principal;
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

}
