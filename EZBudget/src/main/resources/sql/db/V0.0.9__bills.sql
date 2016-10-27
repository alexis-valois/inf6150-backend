CREATE TABLE `ezbudget`.`bills` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` BIT(1) NULL DEFAULT b'0',
  `userId` INT NOT NULL,
  `amount` DECIMAL(20,2) NOT NULL,
  `categorieId` INT NOT NULL,
  `supplierId` INT NULL,
  `accountId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_userid_idx` (`userId` ASC),
  INDEX `fk_accountid_idx` (`accountId` ASC),
  INDEX `fk_categorieid_idx` (`categorieId` ASC),
  INDEX `fk_supplierid_idx` (`supplierId` ASC),
  CONSTRAINT `fk_bills_userid`
    FOREIGN KEY (`userId`)
    REFERENCES `ezbudget`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_accountid`
    FOREIGN KEY (`accountId`)
    REFERENCES `ezbudget`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_categorieid`
    FOREIGN KEY (`categorieId`)
    REFERENCES `ezbudget`.`categories` (`categories_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_supplierid`
    FOREIGN KEY (`supplierId`)
    REFERENCES `ezbudget`.`suppliers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
