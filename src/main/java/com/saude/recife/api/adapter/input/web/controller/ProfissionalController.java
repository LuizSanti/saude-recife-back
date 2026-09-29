package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.AtualizarProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.request.ProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalResponse;
import com.saude.recife.api.adapter.input.web.mapper.ProfissionalWebMapper;
import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.domain.model.Profissional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalUseCase useCase;
    private final ProfissionalWebMapper mapper;

    public ProfissionalController(
            ProfissionalUseCase useCase,
            ProfissionalWebMapper mapper) {

        this.useCase = useCase;
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