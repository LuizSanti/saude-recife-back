package com.saude.recife.api.adapter.input.web.exception;

import com.saude.recife.api.domain.exception.CredenciaisInvalidasException;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.saude.recife.api.domain.exception.EspecialidadeNaoEncontradaException;
import com.saude.recife.api.domain.exception.ClinicaNaoEncontradaException;
import com.saude.recife.api.domain.exception.CnpjJaCadastradoException;
import com.saude.recife.api.domain.exception.CpfJaCadastradoException;
import com.saude.recife.api.domain.exception.ProfissionalNaoEncontradoException;
import com.saude.recife.api.domain.exception.RegistroProfissionalJaCadastradoException;
import com.saude.recife.api.domain.exception.UsuarioJaVinculadoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEhProfissionalException;
import com.saude.recife.api.domain.exception.PacienteNaoEncontradoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEhPacienteException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, String>> handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioInativo(UsuarioInativoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(EspecialidadeNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>>
    handleEspecialidadeNaoEncontrada(
            EspecialidadeNaoEncontradaException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(ClinicaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>>
    handleClinicaNaoEncontrada(
            ClinicaNaoEncontradaException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(CnpjJaCadastradoException.class)
    public ResponseEntity<Map<String, String>>
    handleCnpjJaCadastrado(
            CnpjJaCadastradoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(ProfissionalNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>>
    handleProfissionalNaoEncontrado(
            ProfissionalNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<Map<String, String>>
    handleCpfJaCadastrado(
            CpfJaCadastradoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(
            RegistroProfissionalJaCadastradoException.class
    )
    public ResponseEntity<Map<String, String>>
    handleRegistroProfissionalJaCadastrado(
            RegistroProfissionalJaCadastradoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(UsuarioJaVinculadoException.class)
    public ResponseEntity<Map<String, String>>
    handleUsuarioJaVinculado(
            UsuarioJaVinculadoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(UsuarioNaoEhProfissionalException.class)
    public ResponseEntity<Map<String, String>>
    handleUsuarioNaoEhProfissional(
            UsuarioNaoEhProfissionalException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>>
    handlePacienteNaoEncontrado(
            PacienteNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(UsuarioNaoEhPacienteException.class)
    public ResponseEntity<Map<String, String>>
    handleUsuarioNaoEhPaciente(
            UsuarioNaoEhPacienteException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", ex.getMessage()));
    }
}