package com.saude.recife.api.adapter.input.web.controller;

import com.saude.recife.api.adapter.input.web.dto.request.LoginRequest;
import com.saude.recife.api.adapter.input.web.dto.response.LoginResponse;
import com.saude.recife.api.adapter.input.web.mapper.AutenticacaoWebMapper;
import com.saude.recife.api.application.port.input.AutenticarUsuarioUseCase;
import com.saude.recife.api.application.port.input.ResultadoAutenticacao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final AutenticacaoWebMapper webMapper;

    public AutenticacaoController(AutenticarUsuarioUseCase autenticarUsuarioUseCase, AutenticacaoWebMapper webMapper) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.webMapper = webMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        ResultadoAutenticacao resultado =
                autenticarUsuarioUseCase.autenticar(request.email(), request.senha());

        return ResponseEntity.ok(webMapper.paraLoginResponse(resultado));
    }
}
