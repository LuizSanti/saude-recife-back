package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.CadastroProfissionalUseCase;
import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.application.service.CadastroProfissionalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class CadastroProfissionalConfig {

    @Bean
    public CadastroProfissionalUseCase cadastroProfissionalUseCase(
            UsuarioUseCase usuarioUseCase,
            ProfissionalUseCase profissionalUseCase,
            PlatformTransactionManager tm) {
        CadastroProfissionalUseCase real =
                new CadastroProfissionalService(usuarioUseCase, profissionalUseCase);
        TransactionTemplate tx = new TransactionTemplate(tm);
        return (nome, email, senha, telefone, cpf, conselho, registro, uf) ->
                tx.execute(status -> real.cadastrar(nome, email, senha, telefone,
                        cpf, conselho, registro, uf));
    }
}
