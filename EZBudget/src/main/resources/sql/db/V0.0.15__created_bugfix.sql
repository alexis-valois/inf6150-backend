UPDATE accounts
SET created = CURRENT_TIMESTAMP
WHERE created IS NULL;

UPDATE bills
SET created = CURRENT_TIMESTAMP
WHERE created IS NULL;

UPDATE categories
SET created = CURRENT_TIMESTAMP
WHERE created IS NULL;

UPDATE revenues
SET created = CURRENT_TIMESTAMP
WHERE created IS NULL;

UPDATE suppliers
SET created = CURRENT_TIMESTAMP
WHERE created IS NULL;

CREATE TRIGGER account_SetCreatedAt BEFORE INSERT ON accounts FOR EACH ROW SET NEW.created = NOW();
CREATE TRIGGER bills_SetCreatedAt BEFORE INSERT ON bills FOR EACH ROW SET NEW.created = NOW();
CREATE TRIGGER categories_SetCreatedAt BEFORE INSERT ON categories FOR EACH ROW SET NEW.created = NOW();
CREATE TRIGGER revenues_SetCreatedAt BEFORE INSERT ON revenues FOR EACH ROW SET NEW.created = NOW();
CREATE TRIGGER suppliers_SetCreatedAt BEFORE INSERT ON suppliers FOR EACH ROW SET NEW.created = NOW();