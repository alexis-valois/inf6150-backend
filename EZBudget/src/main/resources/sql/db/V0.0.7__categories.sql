CREATE TABLE `categories` (
  `categories_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `userId` INT NULL,
  `deleted` BIT(1) NOT NULL DEFAULT 0,
  `created` DATETIME NULL DEFAULT now(),
  PRIMARY KEY (`categories_id`),
  INDEX `userId_idx` (`userId` ASC),
  UNIQUE INDEX `categories_id_UNIQUE` (`categories_id` ASC),
  CONSTRAINT `userId_fk`
    FOREIGN KEY (`userId`)
    REFERENCES `ezbudget`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
