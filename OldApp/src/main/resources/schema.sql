CREATE TABLE IF NOT EXISTS clients
(
    guid             VARCHAR(255) NOT NULL,
    agency           VARCHAR(255),
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    status           VARCHAR(255),
    dob              TIMESTAMP WITHOUT TIME ZONE,
    created_datetime TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_clients PRIMARY KEY (guid)
);

CREATE TABLE IF NOT EXISTS notes
(
    guid              VARCHAR(255) NOT NULL,
    comments          VARCHAR(255),
    modified_datetime TIMESTAMP WITHOUT TIME ZONE,
    client_guid       VARCHAR(255),
    date_time         TIMESTAMP WITHOUT TIME ZONE,
    logged_user       VARCHAR(255),
    created_datetime  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_notes PRIMARY KEY (guid)
);