CREATE TABLE terminal
(
    terminal_id  BIGINT         NOT NULL,
    created      TIMESTAMP      NOT NULL DEFAULT now()::timestamp,
    last_updated TIMESTAMP      NOT NULL DEFAULT now()::timestamp,
    PRIMARY KEY (terminal_id)
);

CREATE TABLE message
(
    id              BIGSERIAL,
    created         TIMESTAMP      NOT NULL DEFAULT now()::timestamp,
    last_updated    TIMESTAMP      NOT NULL DEFAULT now()::timestamp,
    message_type    SMALLINT       NOT NULL,
    terminal_id     BIGINT         NOT NULL,
    issuer_id       BIGINT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE issuer
(
    id           BIGSERIAL,
    created      TIMESTAMP    NOT NULL DEFAULT now()::timestamp,
    last_updated TIMESTAMP    NOT NULL DEFAULT now()::timestamp,
    name         VARCHAR(32)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE card_range
(
    id              BIGSERIAL,
    created         TIMESTAMP    NOT NULL DEFAULT now()::timestamp,
    last_updated    TIMESTAMP    NOT NULL DEFAULT now()::timestamp,
    min_card_number INTEGER      NOT NULL,
    max_card_number INTEGER      NOT NULL,
    issuer_id       BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE message
    ADD CONSTRAINT message_terminal_id_fk FOREIGN KEY (terminal_id)
        REFERENCES terminal(terminal_id);

ALTER TABLE message
    ADD CONSTRAINT message_issuer_id_fk FOREIGN KEY (issuer_id)
        REFERENCES issuer(id);

ALTER TABLE issuer
    ADD CONSTRAINT issuer_name_uk UNIQUE (name);

ALTER TABLE card_range
    ADD CONSTRAINT card_range_issuer_id FOREIGN KEY (issuer_id)
        REFERENCES issuer(id);
