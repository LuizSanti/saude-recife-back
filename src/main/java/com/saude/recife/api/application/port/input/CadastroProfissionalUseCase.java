package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Profissional;

public interface CadastroProfissionalUseCase {
    Profissional cadastrar(String nome, String email, String senha, String telefone,
                           String cpf, String conselho, String registroProfissional,
                           String ufRegistro);
}
