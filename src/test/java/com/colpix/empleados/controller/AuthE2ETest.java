package com.colpix.empleados.controller;

import com.colpix.empleados.infraestructure.adapter.UsuarioCrudRepository;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioCrudRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private final String username = "admin_" + UUID.randomUUID();
    private Integer idDelete;

    @BeforeEach
    void setup() {
        UsuarioEntity user = new UsuarioEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setEnabled(true);

        UsuarioEntity userSaved = usuarioRepository.save(user);
        idDelete = userSaved.getId();
    }

    @Test
    void loginYAccesoEndpointProtegido() throws Exception {

        String loginJson = """
        {
          "username": "%s",
          "password": "1234"
        }
        """.formatted(username);

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/api/v1/empleados/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        usuarioRepository.deleteById(idDelete);
    }
}