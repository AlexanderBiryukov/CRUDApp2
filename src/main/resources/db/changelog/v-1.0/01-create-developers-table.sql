CREATE TABLE developers
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50)                NOT NULL,
    last_name    VARCHAR(50)                NOT NULL,
    specialty    VARCHAR(50)                NOT NULL,
    status       ENUM ('ACTIVE', 'DELETED') NOT NULL DEFAULT 'ACTIVE',
    specialty_id INT,
    FOREIGN KEY (specialty_id) REFERENCES specialties (Id) ON DELETE SET NULL
);