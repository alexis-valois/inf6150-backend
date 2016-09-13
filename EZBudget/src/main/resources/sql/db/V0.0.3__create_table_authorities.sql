CREATE TABLE `authorities` (
  `authorities_id` INT NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(45) NULL,
  `fk_username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`authorities_id`),
  INDEX `fk_username_idx` (`fk_username` ASC),
  CONSTRAINT `fk_username`
    FOREIGN KEY (`fk_username`)
    REFERENCES `users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);