package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.CriarDisponibilidadeRequest;
import com.saude.recife.api.adapter.input.web.dto.response.DisponibilidadeResponse;
import com.saude.recife.api.adapter.input.web.mapper.DisponibilidadeWebMapper;
import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadeController {

    private final DisponibilidadeUseCase useCase;
    private final DisponibilidadeWebMapper mapper;

    public DisponibilidadeController(
            DisponibilidadeUseCase useCase,
            DisponibilidadeWebMapper mapper) {

        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DisponibilidadeResponse>> consultar(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data,

            @RequestParam(required = false)
            Long clinicaId,

            @RequestParam(required = false)
            Long especialidadeId,

            @RequestParam(required = false)
            Long profissionalId) {

        List<DisponibilidadeResponse> response =
                useCase.consultar(
                                data,
                                clinicaId,
                                especialidadeId,
                                profissionalId
                        )
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/me")
    public ResponseEntity<List<DisponibilidadeResponse>> listarDoProfissional(
            Authentication authentication) {
        return ResponseEntity.ok(
                useCase.listarDoProfissional(authentication.getName())
                        .stream()
                        .map(mapper::paraResponse)
                        .toList()
        );
    }

    @PostMapping("/profissional/me")
    public ResponseEntity<List<DisponibilidadeResponse>> criarParaProfissional(
            @Valid @RequestBody CriarDisponibilidadeRequest request,
            Authentication authentication) {
        var disponibilidades = useCase.criarParaProfissional(
                request.clinicaId(),
                request.especialidadeId(),
                request.data(),
                request.horaInicio(),
                request.horaFim(),
                authentication.getName()
        );
        return ResponseEntity.ok(
                disponibilidades.stream()
                        .map(mapper::paraResponse)
                        .toList()
        );
    }

    @DeleteMapping("/profissional/me/{id}")
    public ResponseEntity<Void> inativarDoProfissional(
            @PathVariable Long id,
            Authentication authentication) {
        useCase.inativarDoProfissional(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}