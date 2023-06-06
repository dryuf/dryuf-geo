-- Run in database:
--\connect spatialexp

GRANT ALL ON schema public TO spatialexp;

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;
