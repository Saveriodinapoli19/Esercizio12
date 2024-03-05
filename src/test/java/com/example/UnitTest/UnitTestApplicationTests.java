package com.example.UnitTest;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UnitTestApplicationTests {

	@Autowired
	private UserController userController;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;





	@Test
	void createUserTest() throws Exception {
		User user = new User(1L, "Mario", "Rossi");
		mockMvc.perform(post("/user/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.nome").value("Mario"))
				.andExpect(jsonPath("$.cognome").value("Rossi"));
	}

	@Test
	public void getUserById() throws Exception{
		User user = new User(1L, "Mario", "Rossi");
		User userSaved = userRepository.save(user);
		mockMvc.perform(get("/user/{id}",user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value((user.getId())));
	}

	@Test
	public void updateUser() throws Exception{
		User user = new User(1L, "Mario", "Rossi");
		User userSaved = userRepository.save(user);

		User user2 = new User(1L, "Luigi", "Verdi");

		mockMvc.perform(put("/user/update/{id}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user2)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.nome").value("Luigi"))
				.andExpect(jsonPath("$.cognome").value("Verdi"));
	}
	@Test
	void deleteUserById() throws Exception{
		User user = new User(1L, "Mario", "Rossi");
		User userSaved = userRepository.save(user);

		mockMvc.perform(delete("/user/delete/{id}", user.getId()))
				.andExpect(status().isOk());
		mockMvc.perform(get("/user/{id}", user.getId()))
				.andExpect(status().isOk());
	}
}
