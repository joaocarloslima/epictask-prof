
ALTER TABLE epicuser
    ADD score INTEGER;

UPDATE epicuser
SET score = 0
WHERE score IS NULL;

ALTER TABLE epicuser
    ALTER COLUMN score SET NOT NULL;