package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AtualizarUsuarioRequest;
import com.saude.recife.api.adapter.input.web.dto.request.CadastrarUsuarioRequest;
import com.saude.recife.api.adapter.input.web.dto.response.UsuarioResponse;
import com.saude.recife.api.adapter.input.web.mapper.UsuarioWebMapper;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioWebMapper webMapper;

    public UsuarioController(UsuarioUseCase usuarioUseCase, UsuarioWebMapper webMapper) {
        this.usuarioUseCase = usuarioUseCase;
        this.webMapper = webMapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request) {
        Usuario usuario = usuarioUseCase.cadastrar(
                request.nome(), request.email(), request.senha(), request.telefone(), request.tipoUsuario());

        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.paraResponse(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<Usuario> usuarios = usuarioUseCase.listarTodos();
        return ResponseEntity.ok(webMapper.paraListaResponse(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioUseCase.buscarPorId(id);
        return ResponseEntity.ok(webMapper.paraResponse(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody AtualizarUsuarioRequest request) {
        Usuario usuario = usuarioUseCase.atualizar(id, request.nome(), request.telefone(), request.tipoUsuario());
        return ResponseEntity.ok(webMapper.paraResponse(usuario));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioUseCase.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
