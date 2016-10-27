CREATE TABLE `ezbudget`.`revenues` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` BIT(1) NOT NULL DEFAULT b'0',
  `userId` INT NOT NULL,
  `amount` DECIMAL(20,2) NOT NULL,
  `accountId` INT NOT NULL,
  `frequency` VARCHAR(45) NULL DEFAULT 'ONCE',
  `starting` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `ending` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_userid_idx` (`userId` ASC),
  INDEX `fk_accountid_idx` (`accountId` ASC),
  CONSTRAINT `fk_userid`
    FOREIGN KEY (`userId`)
    REFERENCES `ezbudget`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_accountid`
    FOREIGN KEY (`accountId`)
    REFERENCES `ezbudget`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);