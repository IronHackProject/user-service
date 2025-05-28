package com.userService.userService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userService.userService.controller.UserController;
import com.userService.userService.dto.CreateUserRequestDTO;
import com.userService.userService.dto.FindUserByEmailRequestDTO;
import com.userService.userService.exceptions.customExceptions.UserExceptions;
import com.userService.userService.model.User;
import com.userService.userService.repository.UserRepository;
import com.userService.userService.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testCreateUser_shouldReturnOk() throws Exception {
        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO();
        createUserRequestDTO.setName("Abelardo");
        createUserRequestDTO.setSurname("de Juan");
        createUserRequestDTO.setEmail("abelardo@email.es");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestDTO)))
                .andExpect(status().isOk());
    }

    /*
    *
    * Test de validacion de campos obligatorios, salta el @Valid con el @ControllerAdvice
    * y devuelve un BadRequest si no se cumplen las validaciones.
    * */
    @Test
    void testCreateUser_shouldReturnBadRequest() throws Exception {
        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO();
        createUserRequestDTO.setName("Abelardo");
        createUserRequestDTO.setSurname("de Juan");
        // Comento el email para que salte la validacion
        //createUserRequestDTO.setEmail("abelardo@email.es");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequestDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testFindUserByEmail_shouldReturnOk() throws Exception {
        FindUserByEmailRequestDTO findUserByEmailRequestDTO = new FindUserByEmailRequestDTO();
        findUserByEmailRequestDTO.setEmail("abelardo@email.es");
        User mockUser = new User(1L, "Abelardo", "de Juan", "abelardo@email.es");
        when(userService.findUserByEmail(any(FindUserByEmailRequestDTO.class)))
                .thenReturn(ResponseEntity.ok(mockUser));

        mockMvc.perform(get("/api/user/findUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(findUserByEmailRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Abelardo"))
                .andExpect(jsonPath("$.surname").value("de Juan"))
                .andExpect(jsonPath("$.email").value("abelardo@email.es"));
    }

    /*
    *
    * Un test que comprueba que al buscar un usuario por email, si no se encuentra,
    * se lanza una excepcion y se devuelve un BadRequest.
    * */
    @Test
    void testFindUser_missingEmail_shouldReturnValidationError() throws Exception {
        FindUserByEmailRequestDTO dto = new FindUserByEmailRequestDTO();
        dto.setEmail("");

        mockMvc.perform(get("/api/user/findUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testDeleteUser_shouldReturnOk() throws Exception {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/api/user/delete/{id}", userId))
                .andExpect(status().isOk());

    }
    @Test
    void testDeleteUser_userNotFound_shouldReturnBadRequest() throws Exception {
        Long userId = 999L;

        when(userService.deleteUser(userId))
                .thenThrow(new UserExceptions("User not found"));

        mockMvc.perform(delete("/api/user/delete/{id}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    /*
    * Test de uso en el feing Client para buscar un usuario por email.
    *
    * */
    @Test
    void testFindUserByEmail_shouldReturnUser() throws Exception {
        String email = "abelardo@email.es";

        User user = new User(1L, "Abelardo", "de Juan", email);

        when(userService.findUserByEmailOrderService(email))
                .thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(get("/api/user/find/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.surname").value(user.getSurname()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    /*
    *
    * usuario no encontrado por email, se lanza una excepcion y se devuelve un BadRequest.
    * */
    @Test
    void testFindUserByEmail_userNotFound_shouldReturnBadRequest() throws Exception {
        String email = "noexiste@email.es";

        when(userService.findUserByEmailOrderService(email))
                .thenThrow(new UserExceptions("User not found"));

        mockMvc.perform(get("/api/user/find/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("User not found"));
    }
}
