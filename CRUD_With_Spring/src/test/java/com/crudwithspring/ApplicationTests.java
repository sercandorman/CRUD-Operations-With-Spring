package com.crudwithspring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.crudwithspring.controller.MainController;
import com.crudwithspring.model.Person;
import com.crudwithspring.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class ApplicationTests {

	private MockMvc mock;

	@Mock
    private Principal principal;
	
	@Mock
	private PersonService personService;

	@InjectMocks
	private MainController controller;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");
        mock = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
	}
	
	@Test
	public void controllerIndexTest() throws Exception {
		mock.perform(get("/").principal(principal))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}
	
	@Test
	public void controllerLoginTest() throws Exception {
		mock.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}
	
	@Test
	public void controllerAddTest() throws Exception {
		Person person = new Person();
		person.setId(1L);
		person.setName("Test");
		person.setSurname("Add");
		
		mock.perform(post("/add").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(asJsonString(person))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void controllerFindAllTest() throws Exception {
		mock.perform(get("/findAll"))
		.andExpect(status().isOk());
	}
	
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
