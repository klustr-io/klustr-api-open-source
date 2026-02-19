DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_database WHERE datname = '${database}'
    ) THEN
        EXECUTE format('CREATE DATABASE %I', '${database}');
    END IF;
END $$;