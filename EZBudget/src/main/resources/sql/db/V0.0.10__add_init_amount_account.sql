ALTER TABLE `ezbudget`.`accounts` 
ADD COLUMN `initAmount` DECIMAL(20,2) NOT NULL DEFAULT 0.00 AFTER `created`,
ADD COLUMN `currency` VARCHAR(45) NULL DEFAULT 'CAD' AFTER `initAmount`;

ALTER TABLE `ezbudget`.`bills` 
ADD COLUMN `currency` VARCHAR(45) NULL DEFAULT 'CAD' AFTER `accountId`;

