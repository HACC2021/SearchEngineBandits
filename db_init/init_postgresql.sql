CREATE TABLE OWNER
(
    ID            SERIAL      NOT NULL PRIMARY KEY,
    NAME          varchar(50) NOT NULL,
    EMAIL_ADDRESS varchar(100),
    PHONE_NUMBER  varchar(50)
);

CREATE TABLE PET
(
    ID       SERIAL      NOT NULL PRIMARY KEY,
    OWNER_ID int         NOT NULL,
    NAME     varchar(50) NOT NULL,
    CHIP_NO  varchar(50) NOT NULL,
    CONSTRAINT FK_Owner FOREIGN KEY (OWNER_ID) REFERENCES OWNER
);

CREATE TABLE QUARANTINE
(
    ID          SERIAL      NOT NULL PRIMARY KEY,
    PET_ID      int         NOT NULL,
    TRACKING_NO varchar(16) NOT NULL UNIQUE,
    CREATION    timestamp   NOT NULL,
    "end"       timestamp,
    CONSTRAINT FK_Pet FOREIGN KEY (PET_ID) REFERENCES PET
);

CREATE TABLE STATE
(
    ID               SERIAL      NOT NULL PRIMARY KEY,
    QUARANTINE_ID    int         NOT NULL,
    TYPE             varchar(50) NOT NULL,
    CREATION         timestamp   NOT NULL,
    PAYLOAD_TEXT     text,
    PAYLOAD_DATETIME timestamp,
    CONSTRAINT FK_Quarantine FOREIGN KEY (QUARANTINE_ID) REFERENCES QUARANTINE
);

CREATE TABLE ADMIN
(
    ID       SERIAL      NOT NULL PRIMARY KEY,
    USERNAME varchar(50) NOT NULL,
    PASSWORD varchar(50) NOT NULL,
    ROLE     varchar(20)
);