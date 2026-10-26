package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AgendamentoRequest;
import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoResponse;
import com.saude.recife.api.adapter.input.web.mapper.AgendamentoWebMapper;
import com.saude.recife.api.application.port.input.AgendamentoUseCase;
import com.saude.recife.api.domain.model.Agendamento;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoUseCase useCase;
    private final AgendamentoWebMapper mapper;

    public AgendamentoController(
            AgendamentoUseCase useCase,
            AgendamentoWebMapper mapper) {

        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> agendar(
            @Valid
            @RequestBody AgendamentoRequest request,
            Authentication authentication) {

        Agendamento agendamento =
                useCase.agendar(
                        request.disponibilidadeId(),
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        mapper.paraResponse(
                                agendamento
                        )
                );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponse> cancelar(
            @PathVariable Long id,
            Authentication authentication) {

        Agendamento agendamento =
                useCase.cancelar(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                mapper.paraResponse(
                        agendamento
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<AgendamentoResponse>>
    listarMeus(
            Authentication authentication) {

        List<AgendamentoResponse> response =
                useCase
                        .listarMeus(
                                authentication.getName()
                        )
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}