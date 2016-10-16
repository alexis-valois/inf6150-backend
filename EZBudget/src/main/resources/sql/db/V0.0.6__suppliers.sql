CREATE TABLE `suppliers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `userId` INT NULL,
  `created` DATETIME NULL DEFAULT NOW(),
  `deleted` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_suppliers`
    FOREIGN KEY (`userId`)
    REFERENCES `ezbudget`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);