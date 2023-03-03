-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 29 jan. 2023 à 22:22
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `elearning`
--

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

DROP DATABASE IF EXISTS `elearning`;
CREATE Database elearning;

use `elearning`;

DROP TABLE IF EXISTS `classe`;
CREATE TABLE IF NOT EXISTS `classe` (
  `id_classe` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(50) NOT NULL,
  `id_professeur` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_classe`),
  KEY `Professeur` (`id_professeur`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`id_classe`, `libelle`, `id_professeur`) VALUES
(1, 'jee', 3),
(2, 'java', 3),
(3, 'blockchain', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `inscription`
--

DROP TABLE IF EXISTS `inscription`;
CREATE TABLE IF NOT EXISTS `inscription` (
  `id_inscription` int(11) NOT NULL AUTO_INCREMENT,
  `id_etudiant` int(11) NOT NULL,
  `id_classe` int(11) NOT NULL,
  `statut` varchar(50) NOT NULL,
  PRIMARY KEY (`id_inscription`),
  KEY `etudiant` (`id_etudiant`),
  KEY `classe` (`id_classe`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `inscription`
--

INSERT INTO `inscription` (`id_inscription`, `id_etudiant`, `id_classe`, `statut`) VALUES
(1, 2, 1, 'refuser'),
(2, 2, 2, 'accepter'),
(3, 2, 3, 'refuser'),
(4, 4, 1, 'accepter'),
(5, 4, 3, 'accepter'),
(6, 4, 2, 'accepter'),
(7, 2, 1, 'accepter');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id_personne` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `profil` varchar(50) NOT NULL,
  PRIMARY KEY (`id_personne`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id_personne`, `username`, `password`, `profil`) VALUES
(1, 'admin', 'admin', 'admin'),
(2, 'karim', '123', 'etudiant'),
(3, 'hamza', '123', 'professeur'),
(4, 'oussama', '123', 'etudiant'),
(5, 'taha', '123', 'professeur');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `classe`
--
ALTER TABLE `classe`
  ADD CONSTRAINT `Professeur` FOREIGN KEY (`id_professeur`) REFERENCES `personne` (`id_personne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `inscription`
--
ALTER TABLE `inscription`
  ADD CONSTRAINT `classe` FOREIGN KEY (`id_classe`) REFERENCES `classe` (`id_classe`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `etudiant` FOREIGN KEY (`id_etudiant`) REFERENCES `personne` (`id_personne`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;