-- Run as master:

CREATE DATABASE spatialexp;

CREATE USER spatialexp WITH ENCRYPTED PASSWORD 'spatialexp';

GRANT ALL PRIVILEGES ON DATABASE spatialexp TO spatialexp;

