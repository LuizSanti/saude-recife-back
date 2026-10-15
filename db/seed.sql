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

INSERT INTO usuario (nome, email, senha_hash, telefone, tipo_usuario, ativo, criado_em)
VALUES (
'Admin Teste',
'admin@saude.com',
'$2b$12$s20SI8x4Oxvaq32NQpt1fujsQdSdtYTmqL/pCP4BpXGxgPiWKgHTq',
'81988888888',
'ADMIN',
true,
now()
);