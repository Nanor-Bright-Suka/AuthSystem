
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

ALTER TABLE my_users
    ADD COLUMN user_id_tmp UUID;

UPDATE my_users
SET user_id_tmp = gen_random_uuid();

DO $$
DECLARE
pk_name TEXT;
BEGIN
SELECT constraint_name
INTO pk_name
FROM information_schema.table_constraints
WHERE table_name = 'my_users'
  AND constraint_type = 'PRIMARY KEY';

IF pk_name IS NOT NULL THEN
        EXECUTE format('ALTER TABLE my_users DROP CONSTRAINT %I', pk_name);
END IF;
END $$;

ALTER TABLE my_users
DROP COLUMN user_id;

ALTER TABLE my_users
    RENAME COLUMN user_id_tmp TO user_id;

ALTER TABLE my_users
    ADD PRIMARY KEY (user_id);
