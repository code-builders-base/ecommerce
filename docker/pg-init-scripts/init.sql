DO
$do$
    BEGIN
        IF EXISTS (SELECT FROM pg_extension WHERE extname = 'dblink') THEN
            RAISE NOTICE 'Extension already exists';
        ELSE
            CREATE EXTENSION dblink;
        END IF;

        IF EXISTS (SELECT FROM pg_database WHERE datname = 'ecommerce') THEN
            RAISE NOTICE 'Database already exists';
        ELSE
            PERFORM dblink_exec('dbname=' || current_database()
                , 'CREATE DATABASE ecommerce');
        END IF;
    END
$do$
