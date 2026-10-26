package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.CadastroPacienteRequest;
import com.saude.recife.api.adapter.input.web.dto.response.UsuarioResponse;
import com.saude.recife.api.adapter.input.web.mapper.UsuarioWebMapper;
import com.saude.recife.api.application.port.input.CadastroPacienteUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CadastroController {

    private final CadastroPacienteUseCase useCase;
    private final UsuarioWebMapper mapper;

    public CadastroController(CadastroPacienteUseCase useCase, UsuarioWebMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastroPacienteRequest req) {
        var usuario = useCase.cadastrar(req.nome(), req.email(), req.senha(), req.telefone(),
                req.cpf().replaceAll("\\D", ""), req.dataNascimento(), req.sexo(), req.observacoes());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraResponse(usuario));
    }
}
