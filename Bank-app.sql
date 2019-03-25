-- --------------------------------------------------------
-- Värd:                         127.0.0.1
-- Serverversion:                5.7.25-log - MySQL Community Server (GPL)
-- Server-OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumpar databasstruktur för bank-app
CREATE DATABASE IF NOT EXISTS `bank-app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_swedish_ci */;
USE `bank-app`;

-- Dumpar struktur för tabell bank-app.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `account_nr` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `owner_id` varchar(11) COLLATE utf8mb4_swedish_ci NOT NULL,
  `amount` float NOT NULL DEFAULT '0',
  `accounttype` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  PRIMARY KEY (`account_nr`),
  KEY `FK_account_user` (`owner_id`),
  CONSTRAINT `FK_account_user` FOREIGN KEY (`owner_id`) REFERENCES `users` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Dataexport var bortvalt.
-- Dumpar struktur för tabell bank-app.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `to` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `from` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `amount` float NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `FK_transactions_user` (`to`),
  KEY `FK_transactions_user_2` (`from`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Dataexport var bortvalt.
-- Dumpar struktur för tabell bank-app.users
CREATE TABLE IF NOT EXISTS `users` (
  `person_id` varchar(11) COLLATE utf8mb4_swedish_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `surname` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Dataexport var bortvalt.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
