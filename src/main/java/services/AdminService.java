package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import forms.EditionAdminForm;

@Service
@Transactional
public class AdminService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdminRepository adminRepository;

	// Supporting services -----------------------------------------------------

	// Constructor -----------------------------------------------------

	public AdminService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	public Collection<Admin> findAll() {
		Collection<Admin> result;

		result = adminRepository.findAll();

		return result;
	}

	public Admin findOne(int adminId) {
		Assert.notNull(adminId);
		Admin result;

		result = adminRepository.findOne(adminId);

		return result;
	}

	public int save(Admin admin){
		String password;
		Md5PasswordEncoder encoder;
		UserAccount userAccount;
		
		if(admin.getId() == 0) {
			userAccount = admin.getUserAccount();
			encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(userAccount.getPassword(), null);
			
			admin.getUserAccount().setPassword(password);
		}

		Assert.notNull(admin);
		
		adminRepository.save(admin);
		
		return admin.getId();
		
	}
	
	// Other business methods -----------------------------------------------------

		public void checkAdmin(Admin admin) {
			UserAccount principal;
			UserAccount owner;
			   
			principal = LoginService.getPrincipal();   
			owner = admin.getUserAccount();
			
			Assert.notNull(principal);		   
			Assert.isTrue(principal.equals(owner));
		}

		public Integer saveAdmin(Admin admin) {
			Assert.notNull(admin);
			Admin result;

			result = adminRepository.save(admin);

			return result.getId();
		}
		
		public Admin findByPrincipal() {
			
			Admin result;
			UserAccount userAccount;
			
			userAccount = LoginService.getPrincipal();
			result = adminRepository.findByUserAccount(userAccount);
			
			return result;
		}
		
		//Solo se pueden editar los atributos aquí indicados
		public EditionAdminForm fragmentToEdit(Admin admin){
			Assert.notNull(admin);
			EditionAdminForm result;
			
			result = new EditionAdminForm();
			
			result.setId(admin.getId());
			result.setName(admin.getName());
			result.setEmail(admin.getEmail());
			result.setPassword(admin.getUserAccount().getPassword());
			result.setSurname(admin.getSurname());
			result.setNickname(admin.getNickname());
			
			return result;
		}
		
		public Admin reconstructToEdit(EditionAdminForm editionUserForm){
			Admin result;
			UserAccount userAccount;
			Admin principal;
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
}
