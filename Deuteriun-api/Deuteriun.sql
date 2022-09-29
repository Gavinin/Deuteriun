CREATE DATABASE  IF NOT EXISTS `deuteriun` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `deuteriun`;
-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: deuteriun
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `msg_files`
--

DROP TABLE IF EXISTS `msg_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_files` (
  `msg_file_id` bigint NOT NULL AUTO_INCREMENT,
  `msg_id` bigint NOT NULL,
  `file_name` varchar(256) NOT NULL COMMENT 'limit 128',
  `file_addr` varchar(256) NOT NULL,
  `uuid` varchar(64) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` varchar(45) DEFAULT NULL,
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`msg_file_id`),
  KEY `msg_id_idx` (`msg_id`),
  CONSTRAINT `msg_id` FOREIGN KEY (`msg_id`) REFERENCES `msg_list` (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_files`
--

LOCK TABLES `msg_files` WRITE;
/*!40000 ALTER TABLE `msg_files` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_list`
--

DROP TABLE IF EXISTS `msg_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_list` (
  `msg_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` varchar(45) NOT NULL,
  `type` int NOT NULL,
  `content` tinytext,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_list`
--

LOCK TABLES `msg_list` WRITE;
/*!40000 ALTER TABLE `msg_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_files`
--

DROP TABLE IF EXISTS `sys_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_files` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(45) NOT NULL,
  `file_position` varchar(45) NOT NULL,
  `file_token` varchar(45) NOT NULL,
  `create_user_id` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `module_name` varchar(45) DEFAULT NULL,
  `del` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FILE_TOKEN` (`file_token`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_files`
--

LOCK TABLES `sys_files` WRITE;
/*!40000 ALTER TABLE `sys_files` DISABLE KEYS */;
INSERT INTO `sys_files` VALUES (1,'1','1','',1,'2022-01-01 00:00:00','',0);
/*!40000 ALTER TABLE `sys_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_filter_role`
--

DROP TABLE IF EXISTS `sys_filter_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_filter_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sys_role_id` int NOT NULL,
  `filter` varchar(64) NOT NULL,
  `create_user` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_role_id_idx` (`sys_role_id`),
  CONSTRAINT `sys_role_id` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_filter_role`
--

LOCK TABLES `sys_filter_role` WRITE;
/*!40000 ALTER TABLE `sys_filter_role` DISABLE KEYS */;
INSERT INTO `sys_filter_role` VALUES (1,1,'/sys/admin',1,'2020-01-01 00:00:00','2021-01-08 00:00:00'),(2,2,'/apis/user/current-user',1,'2022-01-01 00:00:00','2022-01-01 00:00:00'),(3,2,'/apis/notification/getnotification',1,'2022-01-01 00:00:00','2022-01-01 00:00:00');
/*!40000 ALTER TABLE `sys_filter_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Log id\n',
  `sys_user_id` bigint NOT NULL COMMENT 'user id from sys_user table',
  `event` tinytext NOT NULL COMMENT 'Event detail',
  `event_id` datetime NOT NULL,
  `level` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1:Info\n2:Warning\n3:Error',
  `ip` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_login_jwt_blacklist`
--

DROP TABLE IF EXISTS `sys_login_jwt_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_jwt_blacklist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `user_jwt` text NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_jwt_blacklist`
--

LOCK TABLES `sys_login_jwt_blacklist` WRITE;
/*!40000 ALTER TABLE `sys_login_jwt_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_login_jwt_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_noticfication`
--

DROP TABLE IF EXISTS `sys_noticfication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_noticfication` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) NOT NULL,
  `message` varchar(128) DEFAULT NULL,
  `receive_user_id` bigint NOT NULL,
  `send_user_id` bigint NOT NULL,
  `create_data` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_idx` (`receive_user_id`),
  KEY `send_user` (`send_user_id`),
  CONSTRAINT `reveive_user` FOREIGN KEY (`receive_user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `send_user` FOREIGN KEY (`send_user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_noticfication`
--

LOCK TABLES `sys_noticfication` WRITE;
/*!40000 ALTER TABLE `sys_noticfication` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_noticfication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_code` varchar(32) NOT NULL,
  `role_name` varchar(45) NOT NULL,
  `create_user` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'SYS_ROOT','Default admin',1),(2,'SYS_USER','Default user',1),(3,'ROLE_ANONYMOUS','Any user can  view this filter include guest',1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT 'System unify user id, search user by this key, user can''t change it.',
  `user_nick_name` varchar(32) NOT NULL COMMENT 'User modify name , user can change it.',
  `password` varchar(128) NOT NULL COMMENT 'jwt key',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `del` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'User weather be deleted\n\n0:No\n1:Yes',
  `ban` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'User has been deleted\n0:No\n1:Yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'root','admin','$2a$10$dJaEH/w2GBQunzdHgdPhn.8aQi6bF4UCY4zY4ChhGppllaEO6jZ1.','2022-01-01 00:00:00','2022-01-01 00:00:00',0,0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL,
  `role_id` int NOT NULL COMMENT 'Role string',
  `sys_user_id` bigint NOT NULL COMMENT 'User id from sys_user table',
  `create_role_user_id` bigint NOT NULL COMMENT 'The user id that create this role is',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_idx` (`role_id`),
  CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,1,1,'2022-01-01 00:00:00');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-28 17:35:55
