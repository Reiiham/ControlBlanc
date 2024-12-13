CREATE TABLE `controlblanc`.`membre` (
                                         `identifiant` INT NOT NULL,
                                         `nom` VARCHAR(45) NULL,
                                         `prenom` VARCHAR(45) NULL,
                                         `email` VARCHAR(45) NULL,
                                         `phone` VARCHAR(45) NULL,
                                         `Incident` INT NULL,
                                         PRIMARY KEY (`identifiant`));

CREATE TABLE `controlblanc`.`incident` (
                                           `reference` VARCHAR(30) NOT NULL,
                                           `time` DATETIME(20) NULL,
                                           `status` VARCHAR(45) NULL,
                                           `membre` VARCHAR(45) NULL,
                                           PRIMARY KEY (`reference`));