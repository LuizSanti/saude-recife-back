package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.PacienteUseCase;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.CpfJaCadastradoException;
import com.saude.recife.api.domain.exception.PacienteNaoEncontradoException;
import com.saude.recife.api.domain.exception.UsuarioJaVinculadoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEhPacienteException;
import com.saude.recife.api.domain.exception.UsuarioNaoEncontradoException;
import com.saude.recife.api.domain.model.Paciente;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class PacienteService implements PacienteUseCase {

    private final PacientePort pacientePort;
    private final UsuarioPort usuarioPort;

    public PacienteService(
            PacientePort pacientePort,
            UsuarioPort usuarioPort) {

        this.pacientePort = pacientePort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public Paciente cadastrar(
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String observacoes,
            Long idUsuario) {

        if (pacientePort.existePorCpf(cpf)) {
            throw new CpfJaCadastradoException(cpf);
        }

        if (pacientePort.existePorUsuario(idUsuario)) {
            throw new UsuarioJaVinculadoException(idUsuario);
        }

        Usuario usuario = usuarioPort
                .buscarPorId(idUsuario)
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(idUsuario)
                );

        if (usuario.getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new UsuarioNaoEhPacienteException(idUsuario);
        }

        Paciente paciente = new Paciente(
                null,
                cpf,
                dataNascimento,
                sexo,
                observacoes,
                idUsuario
        );

        return pacientePort.salvar(paciente);
    }

    @Override
    public Paciente buscarPorId(Long id) {

        return pacientePort.buscarPorId(id)
                .orElseThrow(
                        () -> new PacienteNaoEncontradoException(id)
                );
    }

    @Override
    public List<Paciente> listar() {
        return pacientePort.listar();
    }

    @Override
    public Paciente atualizar(
            Long id,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String observacoes) {

        Paciente existente = buscarPorId(id);

        if (!existente.getCpf().equals(cpf)
                && pacientePort.existePorCpf(cpf)) {

            throw new CpfJaCadastradoException(cpf);
        }

        Paciente atualizado = new Paciente(
                existente.getId(),
                cpf,
                dataNascimento,
                sexo,
                observacoes,
                existente.getIdUsuario()
        );

        return pacientePort.salvar(atualizado);
    }
}