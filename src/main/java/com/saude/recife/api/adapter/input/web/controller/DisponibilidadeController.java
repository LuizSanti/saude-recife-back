package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.response.DisponibilidadeResponse;
import com.saude.recife.api.adapter.input.web.mapper.DisponibilidadeWebMapper;
import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}