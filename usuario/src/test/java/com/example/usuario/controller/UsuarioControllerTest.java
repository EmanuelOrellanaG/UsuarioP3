package com.example.usuario.controller;


import com.example.usuario.Controller.UsuarioController;
import com.example.usuario.DTO.UsuarioDTO;
import com.example.usuario.Service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void listarUsuarios_OK() throws Exception {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setEmail("juan@mail.com");

        when(usuarioService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void crearUsuario_OK() throws Exception {

        UsuarioDTO input = new UsuarioDTO();
        input.setNombre("Ana");
        input.setEmail("ana@mail.com");

        UsuarioDTO output = new UsuarioDTO();
        output.setId(1L);
        output.setNombre("Ana");
        output.setEmail("ana@mail.com");

        when(usuarioService.crear(any())).thenReturn(output);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void eliminarUsuario_OK() throws Exception {

        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}


