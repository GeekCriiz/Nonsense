package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {
	// Managed repository -----------------------------------------------
	@Autowired
	private UserAccountRepository userAccountRepository;
		
	// Supporting services ----------------------------------------------

	
	// Constructors -----------------------------------------------------
	
	// Simple CRUD methods ----------------------------------------------
	public UserAccount createPlayer(){
		UserAccount result;
		Authority authority;
		
		result = new UserAccount();
		
		authority = new Authority();
		authority.setAuthority("PLAYER");
		
		result.addAuthority(authority);
		
		return result;
	}
	
	public UserAccount findOne(int id){
		UserAccount result;
		result = userAccountRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}
	
	public UserAccount findUserAccount(UserAccount userAccount) {
		UserAccount result;
		result = userAccountRepository.findByUsername(userAccount.getUsername());
		return result;
	}
	
	public void save(UserAccount userAccount){
		Assert.notNull(userAccount);
		userAccountRepository.save(userAccount);
	}
	
	// Other business methods -------------------------------------------
	
}