package com.saude.recife.api.adapter.input.web.exception;

import com.saude.recife.api.domain.exception.PacienteNaoEncontradoParaUsuarioException;
import com.saude.recife.api.domain.exception.HorarioIndisponivelException;
import org.springframework.dao.DataIntegrityViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void missingPatientProfileReturnsConflictWithActionableMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var response = handler.handlePacienteNaoEncontradoParaUsuario(
                new PacienteNaoEncontradoParaUsuarioException(6L)
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(
                "Esta conta nao possui cadastro de paciente vinculado. Cadastre um paciente antes de agendar.",
                response.getBody().get("erro")
        );
    }

    @Test
    void unavailableAppointmentSlotReturnsConflict() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var response = handler.handleHorarioIndisponivel(
                new HorarioIndisponivelException()
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(
                "Este horário não está disponível para agendamento.",
                response.getBody().get("erro")
        );
    }

    @Test
    void duplicateExamBookingReturnsConflict() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var response = handler.handleViolacaoIntegridade(
                new DataIntegrityViolationException(
                        "duplicate key violates uq_agendamento_exame_horario_confirmado"
                )
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(
                "Este horário já está ocupado para o exame selecionado.",
                response.getBody().get("erro")
        );
    }
}
