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
    id            VARCHAR(255) NOT NULL PRIMARY KEY,
    name_complete VARCHAR(255) NOT NULL,
    doc_value     BIGINT       NOT NULL,
    doc_type      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone         BIGINT       NOT NULL,
    created_at    DATETIME     NOT NULL,
    endereco_id   VARCHAR(255),
    FOREIGN KEY (endereco_id) REFERENCES address (id)
);
