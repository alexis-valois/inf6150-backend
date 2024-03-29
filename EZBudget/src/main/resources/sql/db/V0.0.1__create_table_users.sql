CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `password` CHAR(64) NULL,
  `enabled` BIT(1) NOT NULL DEFAULT b'1',
  `deleted` BIT(1) NOT NULL DEFAULT 0,
  `date_created` DATETIME NOT NULL,
  `last_login` DATETIME NULL,
  `locked` BIT(1) NOT NULL DEFAULT 0,
  `last_logout` DATETIME NULL DEFAULT NULL,
  `credentials_expired` BIT(1) NOT NULL DEFAULT 0,
  `account_expired` BIT(1) NOT NULL DEFAULT 0,
  `activation_token` VARCHAR(80) NULL DEFAULT NULL,
  `email` VARCHAR(150) NOT NULL,
  `session_token` VARCHAR(80) NULL DEFAULT NULL,
  UNIQUE KEY `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`user_id`));