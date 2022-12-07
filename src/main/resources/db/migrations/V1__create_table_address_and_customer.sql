CREATE TABLE address
(
    id           VARCHAR(255) NOT NULL PRIMARY KEY,
    cep          VARCHAR(255),
    logradouro   VARCHAR(255),
    complemento  VARCHAR(255),
    numero       BIGINT,
    bairro       VARCHAR(255),
    localidade   VARCHAR(255),
    uf           VARCHAR(255),
    is_principal BOOLEAN DEFAULT FALSE,
    created_at   DATETIME
);

CREATE TABLE customer
(
    id          VARCHAR(255) NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    cpf         VARCHAR(255) UNIQUE ,
    cnpj        VARCHAR(255) UNIQUE ,
    type        CHAR         NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE ,
    phone       BIGINT       NOT NULL,
    created_at  DATETIME     NOT NULL,
    endereco_id VARCHAR(255),
    FOREIGN KEY (endereco_id) REFERENCES address (id)
);
