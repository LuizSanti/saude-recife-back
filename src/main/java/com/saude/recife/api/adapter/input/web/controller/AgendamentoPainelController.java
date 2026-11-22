package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AtualizarStatusAgendamentoRequest;
import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoPainelResponse;
import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoResponse;
import com.saude.recife.api.adapter.input.web.mapper.AgendamentoPainelWebMapper;
import com.saude.recife.api.adapter.input.web.mapper.AgendamentoWebMapper;
import com.saude.recife.api.adapter.output.persistence.repository.AgendamentoRepository;
import com.saude.recife.api.application.port.input.AgendamentoUseCase;
import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.StatusAgendamento;
import com.saude.recife.api.domain.exception.ProfissionalNaoEncontradoParaUsuarioException;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgendamentoPainelController {
    private final AgendamentoRepository repository;
    private final AgendamentoUseCase useCase;
    private final ProfissionalUseCase profissionalUseCase;
    private final UsuarioUseCase usuarioUseCase;
    private final AgendamentoPainelWebMapper painelMapper;
    private final AgendamentoWebMapper agendamentoMapper;

    public AgendamentoPainelController(
            AgendamentoRepository repository,
            AgendamentoUseCase useCase,
            ProfissionalUseCase profissionalUseCase,
            UsuarioUseCase usuarioUseCase,
            AgendamentoPainelWebMapper painelMapper,
            AgendamentoWebMapper agendamentoMapper) {

        this.repository = repository;
        this.useCase = useCase;
        this.profissionalUseCase = profissionalUseCase;
        this.usuarioUseCase = usuarioUseCase;
        this.painelMapper = painelMapper;
        this.agendamentoMapper = agendamentoMapper;
    }

    @GetMapping("/agendamentos/admin")
    public ResponseEntity<List<AgendamentoPainelResponse>> listarTodos() {
        return ResponseEntity.ok(
                repository.listarPainel()
                        .stream()
                        .map(painelMapper::paraResponse)
                        .toList()
        );
    }

    @GetMapping("/agendamentos/profissional/me")
    public ResponseEntity<List<AgendamentoPainelResponse>> listarAgenda(
            Authentication authentication) {

        var usuario = usuarioUseCase.buscarPorEmail(authentication.getName());
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }
        var profissional = profissionalUseCase.buscarPorUsuario(usuario.getId());
        if (!profissional.isAtivo()) {
            throw new ProfissionalNaoEncontradoParaUsuarioException(usuario.getId());
        }
        return ResponseEntity.ok(
                repository.listarPainelPorProfissional(profissional.getId())
                        .stream()
                        .map(painelMapper::paraResponse)
                        .toList()
        );
    }

    @PatchMapping("/agendamentos/profissional/{id}/status")
    public ResponseEntity<AgendamentoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusAgendamentoRequest request,
            Authentication authentication) {

        StatusAgendamento status = request.status();
        return ResponseEntity.ok(
                agendamentoMapper.paraResponse(
                        useCase.alterarStatusPorProfissional(
                                id,
                                status,
                                request.motivoCancelamento(),
                                authentication.getName()
                        )
                )
        );
    }
}
