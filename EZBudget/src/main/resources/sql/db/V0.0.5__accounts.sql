CREATE TABLE `accounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `userId` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_user_id_idx` (`userId` ASC),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`userId`)
    REFERENCES `ezbudget`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('1', 'CREDITOR', 'MasterCard Platine', '1');
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('2', 'DEBITOR', 'Banque Nationale', '1');

INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('3', 'CREDITOR', 'MasterCard Platine', '2');
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('4', 'DEBITOR', 'Banque Nationale', '2');

INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('5', 'CREDITOR', 'MasterCard Platine', '3');
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('6', 'DEBITOR', 'Banque Nationale', '3');

INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('7', 'CREDITOR', 'MasterCard Platine', '4');
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('8', 'DEBITOR', 'Banque Nationale', '4');

INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('9', 'CREDITOR', 'MasterCard Platine', '5');
INSERT INTO `accounts` (`id`, `type`, `name`, `userId`) VALUES ('10', 'DEBITOR', 'Banque Nationale', '5');
  
