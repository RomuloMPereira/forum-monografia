INSERT INTO tb_categoria(nome) VALUES ('Programação');
INSERT INTO tb_categoria(nome) VALUES ('Infraestrutura');
INSERT INTO tb_categoria(nome) VALUES ('Engenharia de Software');
INSERT INTO tb_categoria(nome) VALUES ('Data Science');
INSERT INTO tb_categoria(nome) VALUES ('Matemática');
INSERT INTO tb_categoria(nome) VALUES ('Assuntos Gerais');

INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Lógica de Programação', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Estrutura de Dados', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Back-end', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Front-end', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Mobile', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Jogos', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Computação Gráfica', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Complexidade de Algoritmos', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Testes', 1);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Sistemas Operacionais', 2);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Banco de Dados', 2);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('UML', 3);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Análise Orientada a Objetos', 3);
INSERT INTO tb_subcategoria(nome, categoria_id) VALUES ('Projeto Orientado a Objetos', 3);

INSERT INTO tb_funcao(autoridade) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_funcao(autoridade) VALUES ('ROLE_ADMIN');

INSERT INTO tb_usuario(email, nome, senha, funcao_id) VALUES ('joao@gmail.com', 'João Mendes', '123456', 2);
INSERT INTO tb_usuario(email, nome, senha, funcao_id) VALUES ('maria@gmail.com', 'Maria da Silva', '123456', 1);
INSERT INTO tb_usuario(email, nome, senha, funcao_id) VALUES ('pedro@gmail.com', 'Pedro dos Santos', '123456', 1);

INSERT INTO tb_topico(corpo, instante, titulo, autor_id, categoria_id, subcategoria_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z', 'Lorem ipsum I', 1, 1, 1);
INSERT INTO tb_topico(corpo, instante, titulo, autor_id, categoria_id, subcategoria_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-08-14T10:00:00Z', 'Lorem ipsum II', 2, 2, 1);
INSERT INTO tb_topico(corpo, instante, titulo, autor_id, categoria_id, subcategoria_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-09-14T10:00:00Z', 'Lorem ipsum III', 3, 2, 2);

INSERT INTO tb_resposta(corpo, instante, autor_id, topico_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-07-15T10:00:00Z', 2, 1);
INSERT INTO tb_resposta(corpo, instante, autor_id, topico_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-07-15T10:00:00Z', 3, 1);
INSERT INTO tb_resposta(corpo, instante, autor_id, topico_id) VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', TIMESTAMP WITH TIME ZONE '2020-08-15T10:00:00Z', 1, 2);

INSERT INTO tb_topicos_curtidos(topico_id, usuario_id) VALUES (1, 2);
INSERT INTO tb_topicos_curtidos(topico_id, usuario_id) VALUES (1, 3);
INSERT INTO tb_topicos_curtidos(topico_id, usuario_id) VALUES (2, 3);

INSERT INTO tb_respostas_curtidas(resposta_id, usuario_id) VALUES (1, 3);
INSERT INTO tb_respostas_curtidas(resposta_id, usuario_id) VALUES (2, 1);
INSERT INTO tb_respostas_curtidas(resposta_id, usuario_id) VALUES (2, 2);


