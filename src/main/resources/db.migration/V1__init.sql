CREATE TABLE authorization
(
    id                            VARCHAR(255) NOT NULL,
    registered_client_id          VARCHAR(255) NULL,
    principal_name                VARCHAR(255) NULL,
    authorization_grant_type      VARCHAR(255) NULL,
    authorized_scopes             TEXT NULL,
    attributes                    TEXT NULL,
    state                         VARCHAR(500) NULL,
    authorization_code_value      TEXT NULL,
    authorization_code_issued_at  datetime NULL,
    authorization_code_expires_at datetime NULL,
    authorization_code_metadata   VARCHAR(255) NULL,
    access_token_value            TEXT NULL,
    access_token_issued_at        datetime NULL,
    access_token_expires_at       datetime NULL,
    access_token_metadata         TEXT NULL,
    access_token_type             VARCHAR(255) NULL,
    access_token_scopes           TEXT NULL,
    refresh_token_value           TEXT NULL,
    refresh_token_issued_at       datetime NULL,
    refresh_token_expires_at      datetime NULL,
    refresh_token_metadata        TEXT NULL,
    oidc_id_token_value           TEXT NULL,
    oidc_id_token_issued_at       datetime NULL,
    oidc_id_token_expires_at      datetime NULL,
    oidc_id_token_metadata        TEXT NULL,
    oidc_id_token_claims          TEXT NULL,
    user_code_value               TEXT NULL,
    user_code_issued_at           datetime NULL,
    user_code_expires_at          datetime NULL,
    user_code_metadata            TEXT NULL,
    device_code_value             TEXT NULL,
    device_code_issued_at         datetime NULL,
    device_code_expires_at        datetime NULL,
    device_code_metadata          TEXT NULL,
    CONSTRAINT pk_authorization PRIMARY KEY (id)
);

CREATE TABLE authorization_consent
(
    registered_client_id VARCHAR(255) NOT NULL,
    principal_name       VARCHAR(255) NOT NULL,
    authorities          VARCHAR(1000) NULL,
    CONSTRAINT pk_authorizationconsent PRIMARY KEY (registered_client_id, principal_name)
);

CREATE TABLE client
(
    id                            VARCHAR(255) NOT NULL,
    client_id                     VARCHAR(255) NULL,
    client_id_issued_at           datetime NULL,
    client_secret                 VARCHAR(255) NULL,
    client_secret_expires_at      datetime NULL,
    client_name                   VARCHAR(255) NULL,
    client_authentication_methods TEXT NULL,
    authorization_grant_types     TEXT NULL,
    redirect_uris                 TEXT NULL,
    post_logout_redirect_uris     TEXT NULL,
    scopes                        TEXT NULL,
    client_settings               TEXT NULL,
    token_settings                TEXT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE session
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    token       VARCHAR(255) NULL,
    expiring_at datetime NULL,
    user_id     BIGINT NULL,
    status      TINYINT NULL,
    CONSTRAINT pk_session PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    e_mail   VARCHAR(255) NOT NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_990d4d377cac9fe1e599b359c UNIQUE (e_mail);

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);


CREATE TABLE categories (
    Id VARCHAR(255) PRIMARY KEY,
    modifiedBy VARCHAR(255),
    modifiedOn TIMESTAMP,
    createdBy VARCHAR(255),
    createdOn TIMESTAMP,
    isDeleted BOOLEAN,
    name VARCHAR(255),
    description VARCHAR(255)
);


CREATE TABLE payments (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    modifiedBy VARCHAR(255),
    modifiedOn TIMESTAMP,
    createdBy VARCHAR(255),
    createdOn TIMESTAMP,
    isDeleted BOOLEAN,
    amount BIGINT,
    status VARCHAR(50),
    user_id BIGINT,
    order_id VARCHAR(255),
    payment_link VARCHAR(255),
    payment_ref VARCHAR(255)
);




-- Create table for products
CREATE TABLE products (
    Id VARCHAR(255) PRIMARY KEY,
    modifiedBy VARCHAR(255),
    modifiedOn TIMESTAMP,
    createdBy VARCHAR(255),
    createdOn TIMESTAMP,
    isDeleted BOOLEAN,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    description TEXT,
    image VARCHAR(255),
    category_id VARCHAR(255),
    price DOUBLE,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(Id)
);



---- Create foreign key constraint for category_id referencing categories
--ALTER TABLE products
--ADD CONSTRAINT fk_product_category
--FOREIGN KEY (category_id)
--REFERENCES categories(Id);



---- Create foreign key constraint for products referencing categories
--ALTER TABLE products
--ADD CONSTRAINT fk_product_category
--FOREIGN KEY (category_id)
--REFERENCES categories(Id);