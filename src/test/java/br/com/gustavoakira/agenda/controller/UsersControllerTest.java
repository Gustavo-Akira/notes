package br.com.gustavoakira.agenda.controller;

import br.com.gustavoakira.agenda.model.LoginDTO;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.repository.NoteRepository;
import br.com.gustavoakira.agenda.repository.UsersRepository;
import br.com.gustavoakira.agenda.security.JWTTokenAuthenticationService;
import br.com.gustavoakira.agenda.service.UserDetailsServiceImpl;
import br.com.gustavoakira.agenda.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@ComponentScan(basePackages = "br.com.gustavoakira.agenda")
@AutoConfigureMockMvc
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserService service;

    @MockBean
    NoteRepository repository;
    @MockBean
    UsersRepository usersRepository;

    JWTTokenAuthenticationService tokenAuthenticationService = new JWTTokenAuthenticationService();

    String getToken() throws Exception {
        String jwt = tokenAuthenticationService.createToken("akirauekita2001@gmail.com");
        return jwt.replace("Bearer","");
    }

    @Test
    @Order(2)
    void getUser() throws Exception {
        getToken();
        mockMvc.perform(get("http://localhost:8080/api/v1/user/{id}",7L).header("Authorization",getToken())).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void saveUser() throws Exception {
        Users users = getValidUser();
        String json = mapper.writeValueAsString(users);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/api/v1/user")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Order(3)
    void updateUser() {
    }

    @Test
    @Order(4)
    void removeUser() throws Exception{
        mockMvc.perform(delete("http://localhost:8080/api/v1/user/{id}",7L).header("Authorization",getToken())).andExpect(status().isOk());
    }

    Users getValidUser(){
        Users user = new Users();
        user.setEmail("akirauekita2002@gmail.com");
        user.setName("Akira");
        user.setPassword("kadeira");
        return user;
    }
}