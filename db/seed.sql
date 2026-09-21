INSERT INTO usuario (nome, email, senha_hash, telefone, tipo_usuario, ativo, criado_em)
VALUES (
'Usuario Teste',
'teste@saude.com',
'$2b$12$rVfFluf.4YPBUM3hRCNzUOqCVKdjjwtui6LtTJqx6Np.GXozny1Bm',
'81999999999',
'PACIENTE',
true,
now()
);