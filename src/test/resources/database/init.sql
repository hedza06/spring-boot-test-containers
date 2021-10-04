CREATE DATABASE IF NOT EXISTS `test-containers`;

-- drop person table if exists
DROP TABLE IF EXISTS `person`;

-- create person table
CREATE TABLE `person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(64) NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` BIT(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identifier_UNIQUE` (`identifier` ASC));