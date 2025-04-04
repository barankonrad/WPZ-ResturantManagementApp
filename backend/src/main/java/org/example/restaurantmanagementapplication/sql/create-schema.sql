CREATE TABLE roles
(
    id   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                         NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(255)                         NOT NULL,
    password VARCHAR(255)                         NOT NULL,
    role_id  INT                                  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);