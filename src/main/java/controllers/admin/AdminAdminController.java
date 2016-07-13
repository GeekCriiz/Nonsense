package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.AdminService;
import controllers.AbstractController;
import domain.Admin;
import forms.EditionAdminForm;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController{

	// Constructors -----------------------------------------------------------
	public AdminAdminController(){
		super();
	}

	// Services ---------------------------------------------------------------
	@Autowired
	private AdminService adminService;

	//Displaying
	@RequestMapping("/see")
	public @ResponseBody Admin seeAdmin(int adminId) {
		Admin result;
		
		try{
			result = adminService.findOne(adminId);
		}catch(Throwable t){
			result = null;
		}
		
		return result;
	}
			
	// Edition -----------------------------------------------------------------
	@RequestMapping("/editAdmin")
	public @ResponseBody EditionAdminForm editAdmin(@RequestBody int adminId) {
		EditionAdminForm result;
		Admin admin;
		
		try{
			admin = adminService.findOne(adminId);
			result = adminService.fragmentToEdit(admin);
		}catch(Throwable t){
			result = null;
		}
			
		return result;
	}
	
	@RequestMapping(value = "/saveAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int save(@RequestBody EditionAdminForm adminForm){
		int result;
		Admin admin;

		try{			
			admin = adminService.reconstructToEdit(adminForm);
			result = adminService.save(admin);
			
		} catch (org.springframework.dao.DataIntegrityViolationException oops) {
		       result = -50;	
		}catch(Throwable t){
			result = -1;
		}

		return result;
	}
}