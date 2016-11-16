CREATE TABLE `ezbudget`.`enums` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `enum_key` VARCHAR(45) NOT NULL,
  `enum_value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('REVENUE_FREQUENCY', 'MONTHLY');
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('REVENUE_FREQUENCY', 'DAILY');
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('REVENUE_FREQUENCY', 'WEEKLY');
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('REVENUE_FREQUENCY', 'BI_WEEKLY');
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('REVENUE_FREQUENCY', 'ONCE');

INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('CURRENCY', 'CAD');
INSERT INTO `ezbudget`.`enums` (`enum_key`, `enum_value`) VALUES ('CURRENCY', 'USD');
