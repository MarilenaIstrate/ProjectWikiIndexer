-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: wiki_indexer
-- ------------------------------------------------------
-- Server version	5.7.13-log

create database if not exists wiki_indexer;
use wiki_indexer;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id_article` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id_article`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
CREATE TABLE `word` (
  `id_word` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(100) NOT NULL,
  `apparitions_nr` int(11) NOT NULL,
  `id_article` int(11) NOT NULL,
  PRIMARY KEY (`id_word`),
  KEY `id_article_idx` (`id_article`),
  CONSTRAINT `id_article` FOREIGN KEY (`id_article`) REFERENCES `article` (`id_article`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2679 DEFAULT CHARSET=utf8;
