-- MySQL dump 10.13  Distrib 5.6.21, for osx10.10 (x86_64)
--
-- Host: localhost    Database: db_auth
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adminatrator`
--

DROP TABLE IF EXISTS `adminatrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adminatrator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adminatrator`
--

LOCK TABLES `adminatrator` WRITE;
/*!40000 ALTER TABLE `adminatrator` DISABLE KEYS */;
INSERT INTO `adminatrator` VALUES (1,NULL,1,'jun.wang',NULL,'110',NULL),(2,NULL,1,'admin',NULL,'110',NULL);
/*!40000 ALTER TABLE `adminatrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adminatrator_role`
--

DROP TABLE IF EXISTS `adminatrator_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adminatrator_role` (
  `adminatrator_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FK327052CF65F7576` (`adminatrator_id`),
  KEY `FK327052CF7589A5BE` (`roles_id`),
  CONSTRAINT `FK327052CF65F7576` FOREIGN KEY (`adminatrator_id`) REFERENCES `adminatrator` (`id`),
  CONSTRAINT `FK327052CF7589A5BE` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adminatrator_role`
--

LOCK TABLES `adminatrator_role` WRITE;
/*!40000 ALTER TABLE `adminatrator_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `adminatrator_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `menu_key` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `system_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK33155FC3DD9A` (`parent_id`),
  KEY `FK33155FF16B7805` (`system_id`),
  CONSTRAINT `FK33155FC3DD9A` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `FK33155FF16B7805` FOREIGN KEY (`system_id`) REFERENCES `system` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2015-01-25 11:08:00','',1,'me','查看我的信息','admin','2015-01-25 11:08:00','/me',NULL,1),(2,'2015-01-25 00:00:00','',1,'getSysInfo','查看系统信息','admin','2015-01-25 11:47:34','/sys',NULL,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_role`
--

DROP TABLE IF EXISTS `menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_role` (
  `roles_id` int(11) NOT NULL,
  `menus_id` int(11) NOT NULL,
  PRIMARY KEY (`roles_id`,`menus_id`),
  KEY `FKA4FEA756616E3310` (`menus_id`),
  KEY `FKA4FEA7567589A5BE` (`roles_id`),
  CONSTRAINT `FKA4FEA756616E3310` FOREIGN KEY (`menus_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `FKA4FEA7567589A5BE` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_role`
--

LOCK TABLES `menu_role` WRITE;
/*!40000 ALTER TABLE `menu_role` DISABLE KEYS */;
INSERT INTO `menu_role` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `pattern` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `system_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEBABC40EF16B7805` (`system_id`),
  CONSTRAINT `FKEBABC40EF16B7805` FOREIGN KEY (`system_id`) REFERENCES `system` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_role`
--

DROP TABLE IF EXISTS `resource_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_role` (
  `resources_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FK3A62CE077589A5BE` (`roles_id`),
  KEY `FK3A62CE07FEFAF36E` (`resources_id`),
  CONSTRAINT `FK3A62CE077589A5BE` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK3A62CE07FEFAF36E` FOREIGN KEY (`resources_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_role`
--

LOCK TABLES `resource_role` WRITE;
/*!40000 ALTER TABLE `resource_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `system_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK358076F16B7805` (`system_id`),
  CONSTRAINT `FK358076F16B7805` FOREIGN KEY (`system_id`) REFERENCES `system` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'2014-12-21 15:02:14','',1,'admin','jun.wang','2014-12-21 15:02:14',1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `web_index` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system`
--

LOCK TABLES `system` WRITE;
/*!40000 ALTER TABLE `system` DISABLE KEYS */;
INSERT INTO `system` VALUES (1,NULL,'auth-sdk example',1,'example','admin','2015-01-02 12:42:48','localhost:8889');
/*!40000 ALTER TABLE `system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isdel` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_state` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `system_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCBF16B7805` (`system_id`),
  CONSTRAINT `FK36EBCBF16B7805` FOREIGN KEY (`system_id`) REFERENCES `system` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'2015-01-25 10:48:47','admin@peaceful.com',1,NULL,'admin','92e95ec63a2946f8225ac7fe2d54a890',0,'2015-01-25 10:48:47',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FK143BF46A7589A5BE` (`roles_id`),
  KEY `FK143BF46A758CC9E8` (`users_id`),
  CONSTRAINT `FK143BF46A7589A5BE` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46A758CC9E8` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (4,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-25 12:10:17
