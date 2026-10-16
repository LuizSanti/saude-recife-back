package com.saude.recife.api.domain.exception;

public class RegistroProfissionalJaCadastradoException
        extends RuntimeException {

    public RegistroProfissionalJaCadastradoException(
            String registro,
            String conselho,
            String uf) {

        super(
                "Registro profissional já cadastrado: "
                        + registro
                        + " / "
                        + conselho
                        + " / "
                        + uf
        );
    }
}