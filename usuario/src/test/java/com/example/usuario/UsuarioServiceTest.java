package com.example.usuario;

import com.example.usuario.DTO.UsuarioDTO;
import com.example.usuario.Entity.Usuario;
import com.example.usuario.Repository.UsuarioRepository;
import com.example.usuario.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void debeListarUsuarios() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@test.com");
        usuario.setTelefono("123456789");

        when(usuarioRepository.findAll())
                .thenReturn(Arrays.asList(usuario));

        var resultado = usuarioService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void debeObtenerUsuarioPorId() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Pedro");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UsuarioDTO dto = usuarioService.obtenerPorId(1L);

        assertEquals("Pedro", dto.getNombre());
    }

    @Test
    void debeCrearUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("María");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("María");

        UsuarioDTO resultado = usuarioService.crear(dto);

        assertEquals("María", resultado.getNombre());
    }

    @Test
    void debeActualizarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Viejo");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Nuevo");
        dto.setApellido("Apellido");
        dto.setEmail("nuevo@test.com");
        dto.setTelefono("999999");

        UsuarioDTO resultado = usuarioService.actualizar(1L, dto);

        assertEquals("Nuevo", resultado.getNombre());
    }

    @Test
    void debeEliminarUsuario() {

        when(usuarioRepository.existsById(1L))
                .thenReturn(true);

        usuarioService.eliminar(1L);

        verify(usuarioRepository, times(1))
                .deleteById(1L);
    }
}