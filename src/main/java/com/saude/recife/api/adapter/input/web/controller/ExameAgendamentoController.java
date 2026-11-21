package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AgendamentoExameRequest;
import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoExameResponse;
import com.saude.recife.api.adapter.input.web.dto.response.DisponibilidadeExameResponse;
import com.saude.recife.api.adapter.input.web.dto.response.ExameResponse;
import com.saude.recife.api.adapter.input.web.mapper.ExameAgendamentoWebMapper;
import com.saude.recife.api.application.port.input.ExameAgendamentoUseCase;
import com.saude.recife.api.domain.model.AgendamentoExame;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExameAgendamentoController {

    private final ExameAgendamentoUseCase useCase;
    private final ExameAgendamentoWebMapper mapper;

    public ExameAgendamentoController(
            ExameAgendamentoUseCase useCase,
            ExameAgendamentoWebMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping("/exames")
    public ResponseEntity<List<ExameResponse>> listarExames() {
        return ResponseEntity.ok(
                useCase.listarExames()
                        .stream()
                        .map(mapper::paraResponse)
                        .toList()
        );
    }

    @GetMapping("/disponibilidades-exame")
    public ResponseEntity<List<DisponibilidadeExameResponse>> consultarDisponibilidades(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data,
            @RequestParam(required = false) Long clinicaId,
            @RequestParam(required = false) Long exameId) {
        return ResponseEntity.ok(
                useCase.consultarDisponibilidades(data, clinicaId, exameId)
                        .stream()
                        .map(mapper::paraResponse)
                        .toList()
        );
    }

    @PostMapping("/agendamentos-exame")
    public ResponseEntity<AgendamentoExameResponse> agendar(
            @Valid @RequestBody AgendamentoExameRequest request,
            Authentication authentication) {
        AgendamentoExame agendamento = useCase.agendar(
                request.disponibilidadeId(),
                authentication.getName()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.paraResponse(agendamento));
    }
}
