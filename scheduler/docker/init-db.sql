CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS t_jobs (
        id         UUID PRIMARY KEY,
        job_name   VARCHAR(255) NOT NULL UNIQUE,
        try_column VARCHAR(255) NOT NULL
    );

INSERT INTO t_jobs (id, job_name, try_column)
SELECT
    uuid_generate_v4(),
    'job-' || gs,
    'try-' || gs
FROM generate_series(1, 100000) AS gs;