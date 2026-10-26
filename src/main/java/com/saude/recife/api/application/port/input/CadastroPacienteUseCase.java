package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Usuario;
import java.time.LocalDate;

public interface CadastroPacienteUseCase {
    Usuario cadastrar(String nome, String email, String senha, String telefone,
                      String cpf, LocalDate dataNascimento, String sexo, String observacoes);
}
