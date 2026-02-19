REVOKE ALL ON DATABASE "${database}" FROM PUBLIC;

REVOKE ALL ON SCHEMA public FROM PUBLIC;
ALTER SCHEMA public OWNER TO "${username}";
GRANT USAGE, CREATE ON SCHEMA public TO "${username}";

ALTER ROLE "${username}" SET search_path = public;

REVOKE SELECT ON pg_catalog.pg_user  FROM "${username}";
REVOKE SELECT ON pg_catalog.pg_roles FROM "${username}";

ALTER DEFAULT PRIVILEGES FOR ROLE "${username}" IN SCHEMA public
    GRANT ALL ON TABLES TO "${username}";

ALTER DEFAULT PRIVILEGES FOR ROLE "${username}" IN SCHEMA public
    GRANT ALL ON SEQUENCES TO "${username}";

REVOKE CONNECT ON DATABASE postgres FROM PUBLIC;

REVOKE CONNECT ON DATABASE postgres FROM "${username}";

REVOKE SELECT ON pg_catalog.pg_roles FROM PUBLIC;

REVOKE SELECT ON pg_catalog.pg_user  FROM PUBLIC;

REVOKE SELECT ON pg_catalog.pg_authid FROM PUBLIC;