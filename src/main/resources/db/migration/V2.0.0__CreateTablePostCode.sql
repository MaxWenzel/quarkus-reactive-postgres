CREATE TABLE postalcode (
    id SERIAL NOT NULL PRIMARY KEY,
    postalcode VARCHAR(7) NOT NULL,
    locality VARCHAR(255) NOT NULL,
    state VARCHAR(31) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    unique (postalcode, locality)
);
GRANT SELECT, INSERT, UPDATE ON TABLE postalcode TO quarkus;
GRANT ALL ON SEQUENCE postalcode_id_seq TO quarkus;