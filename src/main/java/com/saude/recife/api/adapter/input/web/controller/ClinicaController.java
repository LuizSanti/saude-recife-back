package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.ClinicaRequest;
import com.saude.recife.api.adapter.input.web.dto.response.ClinicaResponse;
import com.saude.recife.api.adapter.input.web.mapper.ClinicaWebMapper;
import com.saude.recife.api.application.port.input.ClinicaUseCase;
import com.saude.recife.api.domain.model.Clinica;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinicas")
public class ClinicaController {

    private final ClinicaUseCase useCase;
    private final ClinicaWebMapper mapper;

    public ClinicaController(
            ClinicaUseCase useCase,
            ClinicaWebMapper mapper) {

        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ClinicaResponse> cadastrar(
            @Valid @RequestBody ClinicaRequest request) {

        Clinica clinica = useCase.cadastrar(
                request.nome(),
                request.cnpj(),
                request.telefone(),
                request.email(),
                request.logradouro(),
                request.numero(),
                request.bairro(),
                request.cidade(),
                request.uf()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.paraResponse(clinica));
    }

    @GetMapping
    public ResponseEntity<List<ClinicaResponse>> listar() {

        List<ClinicaResponse> response =
                useCase.listar()
                        .stream()
                        .map(mapper::paraResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicaResponse> buscarPorId(
            @PathVariable Long id) {

        Clinica clinica =
                useCase.buscarPorId(id);

        return ResponseEntity.ok(
                mapper.paraResponse(clinica)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClinicaRequest request) {

        Clinica clinica = useCase.atualizar(
                id,
                request.nome(),
                request.cnpj(),
                request.telefone(),
                request.email(),
                request.logradouro(),
                request.numero(),
                request.bairro(),
                request.cidade(),
                request.uf()
        );

        return ResponseEntity.ok(
                mapper.paraResponse(clinica)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(
            @PathVariable Long id) {

        useCase.inativar(id);

        return ResponseEntity.noContent().build();
    }
}