DO $$
BEGIN
    IF NOT has_database_privilege('${username}', '${database}', 'CONNECT') THEN
        EXECUTE format('GRANT CONNECT ON DATABASE %I TO %I', '${database}', '${username}');
    END IF;
END $$;
