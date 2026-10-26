package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.CadastroPacienteUseCase;
import com.saude.recife.api.application.port.input.PacienteUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.application.service.CadastroPacienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class CadastroPacienteConfig {

    @Bean
    public CadastroPacienteUseCase cadastroPacienteUseCase(UsuarioUseCase usuarioUseCase,
                                                           PacienteUseCase pacienteUseCase,
                                                           PlatformTransactionManager tm) {
        CadastroPacienteUseCase real = new CadastroPacienteService(usuarioUseCase, pacienteUseCase);
        TransactionTemplate tx = new TransactionTemplate(tm);
        return (nome, email, senha, telefone, cpf, nascimento, sexo, obs) ->
                tx.execute(status -> real.cadastrar(nome, email, senha, telefone, cpf, nascimento, sexo, obs));
    }
}