-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: yanglao
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `yanglao_device_files`
--

DROP TABLE IF EXISTS `yanglao_device_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yanglao_device_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备号',
  `file_name` varchar(50) NOT NULL COMMENT '文件名',
  `file_type` varchar(20) NOT NULL COMMENT '文件类型',
  `file_path` varchar(100) NOT NULL COMMENT '文件储存路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `device_id_foreign_idx` (`device_id`),
  CONSTRAINT `device_id_foreign` FOREIGN KEY (`device_id`) REFERENCES `yanglao_device_id` (`device_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yanglao_device_files`
--

LOCK TABLES `yanglao_device_files` WRITE;
/*!40000 ALTER TABLE `yanglao_device_files` DISABLE KEYS */;
/*!40000 ALTER TABLE `yanglao_device_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yanglao_device_id`
--

DROP TABLE IF EXISTS `yanglao_device_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yanglao_device_id` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_id_unique` (`device_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yanglao_device_id`
--

LOCK TABLES `yanglao_device_id` WRITE;
/*!40000 ALTER TABLE `yanglao_device_id` DISABLE KEYS */;
INSERT INTO `yanglao_device_id` VALUES (1,1,1,'2018-11-28 15:55:50','2018-11-28 15:55:50');
/*!40000 ALTER TABLE `yanglao_device_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yanglao_device_logs`
--

DROP TABLE IF EXISTS `yanglao_device_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yanglao_device_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备号',
  `temp` double NOT NULL COMMENT '温度',
  `humi` double NOT NULL COMMENT '湿度',
  `gus` double NOT NULL COMMENT '可燃气体浓度',
  `inf` int(2) NOT NULL COMMENT '红外',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yanglao_device_logs`
--

LOCK TABLES `yanglao_device_logs` WRITE;
/*!40000 ALTER TABLE `yanglao_device_logs` DISABLE KEYS */;
INSERT INTO `yanglao_device_logs` VALUES (1,1,12.43,34.53,3.4,0,'2018-11-15 10:57:42'),(2,1,12.43,54.53,3.4,0,'2018-11-15 10:58:05'),(3,1,12.43,34.53,8.4,0,'2018-11-15 10:58:56'),(4,1,17.43,35.53,9.4,1,'2018-11-28 15:30:03');
/*!40000 ALTER TABLE `yanglao_device_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yanglao_user`
--

DROP TABLE IF EXISTS `yanglao_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yanglao_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码(MD5加密)',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `role` int(4) NOT NULL COMMENT '角色:0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yanglao_user`
--

LOCK TABLES `yanglao_user` WRITE;
/*!40000 ALTER TABLE `yanglao_user` DISABLE KEYS */;
INSERT INTO `yanglao_user` VALUES (1,'test1','E10ADC3949BA59ABBE56E057F20F883E','11111111111',0,'2018-10-24 22:16:17','2018-10-24 22:16:17'),(2,'n5438','E10ADC3949BA59ABBE56E057F20F883E','11595956231',0,'2018-10-25 10:53:25','2018-10-25 10:53:25'),(3,'test2','E10ADC3949BA59ABBE56E057F20F883E','11111111112',0,'2018-10-26 16:45:02','2018-10-26 16:45:02'),(4,'test3','E10ADC3949BA59ABBE56E057F20F883E','11111111113',0,'2018-11-18 23:21:11','2018-11-18 23:21:11');
/*!40000 ALTER TABLE `yanglao_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-06 21:18:28
