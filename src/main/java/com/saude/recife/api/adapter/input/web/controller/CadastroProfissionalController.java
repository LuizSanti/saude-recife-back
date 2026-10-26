package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.CadastroProfissionalRequest;
import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalResponse;
import com.saude.recife.api.adapter.input.web.mapper.ProfissionalWebMapper;
import com.saude.recife.api.application.port.input.CadastroProfissionalUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissionais")
public class CadastroProfissionalController {

    private final CadastroProfissionalUseCase useCase;
    private final ProfissionalWebMapper mapper;

    public CadastroProfissionalController(CadastroProfissionalUseCase useCase,
                                          ProfissionalWebMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping("/cadastro-completo")
    public ResponseEntity<ProfissionalResponse> cadastrar(
            @Valid @RequestBody CadastroProfissionalRequest req) {
        var profissional = useCase.cadastrar(req.nome(), req.email(), req.senha(),
                req.telefone(), req.cpf().replaceAll("\\D", ""), req.conselho(),
                req.registroProfissional(), req.ufRegistro().toUpperCase());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.paraResponse(profissional)); // AJUSTAR
    }
}
