package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AtualizarProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.request.AtualizarPerfilProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.request.ProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalResponse;
import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalPerfilResponse;
import com.saude.recife.api.adapter.input.web.mapper.ProfissionalWebMapper;
import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import com.saude.recife.api.domain.exception.ProfissionalNaoEncontradoParaUsuarioException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalUseCase useCase;
    private final UsuarioUseCase usuarioUseCase;
    private final ProfissionalWebMapper mapper;

    public ProfissionalController(
            ProfissionalUseCase useCase,
            UsuarioUseCase usuarioUseCase,
            ProfissionalWebMapper mapper) {

        this.useCase = useCase;
        this.usuarioUseCase = usuarioUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProfissionalResponse> cadastrar(
            @Valid
            @RequestBody
            ProfissionalRequest request) {

        Profissional profissional =
                useCase.cadastrar(
                        request.cpf(),
                        request.conselho(),
                        request.registroProfissional(),
                        request.ufRegistro(),
                        request.idUsuario()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.paraResponse(profissional));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponse>> listar() {

        List<ProfissionalResponse> response =
                useCase.listar()
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfissionalPerfilResponse> buscarMeuPerfil(
            Authentication authentication) {

        var usuario = usuarioUseCase.buscarPorEmail(authentication.getName());
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }
        return ResponseEntity.ok(
                mapper.paraPerfilResponse(obterProfissionalAtivo(usuario.getId()))
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ProfissionalPerfilResponse> atualizarMeuPerfil(
            @Valid @RequestBody AtualizarPerfilProfissionalRequest request,
            Authentication authentication) {

        var usuario = usuarioUseCase.buscarPorEmail(authentication.getName());
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        usuarioUseCase.atualizar(
                usuario.getId(),
                request.nome(),
                request.telefone(),
                usuario.getTipoUsuario()
        );

        return ResponseEntity.ok(
                mapper.paraPerfilResponse(obterProfissionalAtivo(usuario.getId()))
        );
    }

    private Profissional obterProfissionalAtivo(Long idUsuario) {
        Profissional profissional = useCase.buscarPorUsuario(idUsuario);
        if (!profissional.isAtivo()) {
            throw new ProfissionalNaoEncontradoParaUsuarioException(idUsuario);
        }
        return profissional;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                mapper.paraResponse(
                        useCase.buscarPorId(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> atualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody
            AtualizarProfissionalRequest request) {

        Profissional profissional =
                useCase.atualizar(
                        id,
                        request.cpf(),
                        request.conselho(),
                        request.registroProfissional(),
                        request.ufRegistro()
                );

        return ResponseEntity.ok(
                mapper.paraResponse(profissional)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(
            @PathVariable Long id) {

        useCase.inativar(id);

        return ResponseEntity.noContent().build();
    }
}