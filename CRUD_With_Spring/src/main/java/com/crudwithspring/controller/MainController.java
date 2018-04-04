package com.crudwithspring.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.crudwithspring.model.Person;
import com.crudwithspring.service.PersonService;

@RestController
public class MainController {

	ModelAndView mavIndex = new ModelAndView("index"); // Goruntulenecek sayfanin adi (view page name)

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/")
	public ModelAndView index(Principal principal) {
		String user = principal.getName();  // Login olan kullanicinin username'i | Username of the login user
		mavIndex.addObject("principal", user);
		return mavIndex;
	}

	// Logout olduktan sonra mod view_records'da kalirsa tekrar login olunca kayitlar
	// otomatik olarak gorunecektir. Bunu engellemek icin farkli bir moda giriyoruz.
	// Once the logout is done, the mode will be still in view_records and the records will be displayed automatically
	// when you log in again. We are entering a different mode to prevent this.
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		mavIndex.addObject("mod", "HIDE_RECORDS");
		return new ModelAndView("login"); 
	}

	// Show Records butonuna bastiktan sonra Jquery bu metoda bir Get istegi yapacak ve daha sonra
	// thymeleaf switch ile mod = view_record oldugu icin kayitlar goruntulenecektir.
	// After clicking on the Show Records button, Jquery will make a Get request to this method.
	// Then, the records will be displayed as thymeleaf switch with mod = view_record.
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public void findAll() {
		mavIndex.addObject("allRecords", personService.findAll());
		mavIndex.addObject("mod", "VIEW_RECORDS");
	}

	// Duzenleme butonuna basilinca duzenleme sayfasinin goruntulenmesini saglar
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public void findOne(@RequestParam long id, HttpServletResponse hsr) throws IOException {
		mavIndex.addObject("getOne", personService.findOne(id));
		mavIndex.addObject("mod", "EDIT_RECORDS"); // th:switch mod = EDIT_RECORDS oldugundan duzenleme div'i gorunecek
		// Edit form will be displayed because of th:switch mod = EDIT_RECORDS
		hsr.sendRedirect("/");
	}

	// Edit'teki degisikliklerin Save butonuna bastiktan sonra veritabanina kaydedilmesini saglar.
	// Changes in Edit will be saved in the database after clicking the Save button.
	@RequestMapping("/update")
	public void save(Person kisi, BindingResult result, HttpServletResponse hsr) throws IOException {
		personService.save(kisi); 
		mavIndex.addObject("allRecords", personService.findAll());
		mavIndex.addObject("mod", "VIEW_RECORDS"); // Daha sonra yine tumm kayitlari gorebilmek icin ...
		// Then to see all the records again...
		hsr.sendRedirect("/");
	}

	// Yeni kayit eklenmesini saglar | Add new record
	@PostMapping(value = "/add")
	public void add(@RequestBody Person kisi, HttpServletResponse hsr) throws IOException {
		personService.save(kisi);
		mavIndex.addObject("allRecords", personService.findAll());
	}

	// Kaydin silinmesini saglar | Delete record
	@RequestMapping("/delete")
	public void delete(@RequestParam long id, HttpServletResponse hsr) throws IOException {
		personService.delete(id);
		mavIndex.addObject("allRecords", personService.findAll());
		hsr.sendRedirect("/");
	}
}