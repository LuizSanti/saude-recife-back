package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.EspecialidadeRequest;
import com.saude.recife.api.adapter.input.web.dto.response.EspecialidadeResponse;
import com.saude.recife.api.adapter.input.web.mapper.EspecialidadeWebMapper;
import com.saude.recife.api.application.port.input.EspecialidadeUseCase;
import com.saude.recife.api.domain.model.Especialidade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeUseCase useCase;
    private final EspecialidadeWebMapper mapper;

    public EspecialidadeController(
            EspecialidadeUseCase useCase,
            EspecialidadeWebMapper mapper) {

        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponse> cadastrar(
            @Valid @RequestBody EspecialidadeRequest request) {

        Especialidade especialidade =
                useCase.cadastrar(
                        request.nome(),
                        request.descricao()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.paraResponse(especialidade));
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponse>> listar() {

        List<EspecialidadeResponse> response =
                useCase.listar()
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponse> buscarPorId(
            @PathVariable Long id) {

        Especialidade especialidade =
                useCase.buscarPorId(id);

        return ResponseEntity.ok(
                mapper.paraResponse(especialidade)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecialidadeRequest request) {

        Especialidade especialidade =
                useCase.atualizar(
                        id,
                        request.nome(),
                        request.descricao()
                );

        return ResponseEntity.ok(
                mapper.paraResponse(especialidade)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(
            @PathVariable Long id) {

        useCase.inativar(id);

        return ResponseEntity.noContent().build();
    }
}