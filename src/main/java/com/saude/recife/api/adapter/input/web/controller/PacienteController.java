package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AtualizarPacienteRequest;
import com.saude.recife.api.adapter.input.web.dto.request.PacienteRequest;
import com.saude.recife.api.adapter.input.web.dto.request.AtualizarPerfilPacienteRequest;
import com.saude.recife.api.adapter.input.web.dto.response.PacienteResponse;
import com.saude.recife.api.adapter.input.web.dto.response.PerfilPacienteResponse;
import com.saude.recife.api.adapter.input.web.mapper.PacienteWebMapper;
import com.saude.recife.api.application.port.input.PacienteUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import com.saude.recife.api.domain.model.Paciente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteUseCase useCase;
    private final UsuarioUseCase usuarioUseCase;
    private final PacienteWebMapper mapper;

    public PacienteController(
            PacienteUseCase useCase,
            UsuarioUseCase usuarioUseCase,
            PacienteWebMapper mapper) {

        this.useCase = useCase;
        this.usuarioUseCase = usuarioUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> cadastrar(
            @Valid @RequestBody PacienteRequest request) {

        Paciente paciente = useCase.cadastrar(
                request.cpf(),
                request.dataNascimento(),
                request.sexo(),
                request.observacoes(),
                request.idUsuario()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.paraResponse(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar() {

        List<PacienteResponse> response =
                useCase.listar()
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<PerfilPacienteResponse> buscarMeuPerfil(
            Authentication authentication) {
        var usuario = usuarioUseCase.buscarPorEmail(authentication.getName());
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }
        return ResponseEntity.ok(mapper.paraPerfilResponse(
                useCase.buscarPorUsuario(usuario.getId()),
                usuario
        ));
    }

    @PutMapping("/me")
    public ResponseEntity<PerfilPacienteResponse> atualizarMeuPerfil(
            @Valid @RequestBody AtualizarPerfilPacienteRequest request,
            Authentication authentication) {
        var usuario = usuarioUseCase.buscarPorEmail(authentication.getName());
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }
        var atualizado = usuarioUseCase.atualizar(
                usuario.getId(),
                request.nome(),
                request.telefone(),
                usuario.getTipoUsuario()
        );
        return ResponseEntity.ok(mapper.paraPerfilResponse(
                useCase.buscarPorUsuario(usuario.getId()),
                atualizado
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(
            @PathVariable Long id) {

        Paciente paciente = useCase.buscarPorId(id);

        return ResponseEntity.ok(
                mapper.paraResponse(paciente)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarPacienteRequest request) {

        Paciente paciente = useCase.atualizar(
                id,
                request.cpf(),
                request.dataNascimento(),
                request.sexo(),
                request.observacoes()
        );

        return ResponseEntity.ok(
                mapper.paraResponse(paciente)
        );
    }
}