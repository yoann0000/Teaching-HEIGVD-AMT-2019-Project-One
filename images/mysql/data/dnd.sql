-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------             
-- * Generation date: Wed Oct 16 08:52:25 2019 
-- ********************************************* 
-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS dnd;
CREATE DATABASE dnd;
USE dnd;
-- DBSpace Section
-- _______________
-- Tables Section
CREATE TABLE classe (     
	id VARCHAR(30),        
	CONSTRAINT ID_Classe PRIMARY KEY (id)
);

CREATE TABLE race (     
	id VARCHAR(30),        
	CONSTRAINT ID_race PRIMARY KEY (id)
);

CREATE TABLE player (        
	id VARCHAR(100) NOT NULL,  
	password VARCHAR(100) NOT NULL,  	
	gold BIGINT UNSIGNED NOT NULL,
	strength SMALLINT UNSIGNED NOT NULL,
	dexterity SMALLINT UNSIGNED NOT NULL,
	constitution SMALLINT UNSIGNED NOT NULL,
	intelligence SMALLINT UNSIGNED NOT NULL,
	wisdom SMALLINT UNSIGNED NOT NULL,
	charisma SMALLINT UNSIGNED NOT NULL,
	experience INT UNSIGNED NOT NULL,
	spendpoints INT UNSIGNED NOT NULL,
	fkRace VARCHAR(30) NOT NULL,     
	fkClasse VARCHAR(30) NOT NULL,
	fkGuild VARCHAR(100),
	CONSTRAINT ID_player PRIMARY KEY (id)
);

CREATE TABLE guild(
	id VARCHAR(100) NOT NULL,
	reputation INT NOT NULL,
	CONSTRAINT ID_guild PRIMARY KEY (id)
);

CREATE TABLE party(
	id VARCHAR(100) NOT NULL,
	reputation INT NOT NULL,
	CONSTRAINT ID_party PRIMARY KEY (id)
);

CREATE TABLE quest(
	id INT UNSIGNED NOT NULL,
	objective VARCHAR(50) NOT NULL,
	description VARCHAR(666) NOT NULL,
	rewardGold INT UNSIGNED NOT NULL,
	rewardExp INT UNSIGNED NOT NULL,
	CONSTRAINT ID_quest PRIMARY KEY (id)
);

CREATE TABLE playerParty(
	fkPlayer VARCHAR(100) NOT NULL,     
	fkParty VARCHAR(100) NOT NULL,     
	CONSTRAINT ID_partyPlayer PRIMARY KEY (fkPlayer, fkParty)
);

CREATE TABLE guildPartyQuest(
	fkGuild VARCHAR(100)NOT NULL,
	fkParty VARCHAR(100) NOT NULL,
	fkQuest INT UNSIGNED NOT NULL,
	temps DATETIME NOT NULL,
	CONSTRAINT ID_guildPartyQuest PRIMARY KEY (fkGuild, fkParty, fkQuest)
);

-- Constraints Section
-- ___________________ 
ALTER TABLE player 
	ADD CONSTRAINT FKplayerClasse
	FOREIGN KEY (fkClasse)     
	REFERENCES classe (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE player 
	ADD CONSTRAINT FKplayerRace
	FOREIGN KEY (fkRace)     
	REFERENCES race (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE player 
	ADD CONSTRAINT FKplayerGuild
	FOREIGN KEY (fkGuild)     
	REFERENCES guild (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyPlayer
	FOREIGN KEY (fkPlayer)     
	REFERENCES player (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE playerParty
	ADD CONSTRAINT FKplayerPartyParty
	FOREIGN KEY (fkParty)     
	REFERENCES party (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE guildPartyQuest
	ADD CONSTRAINT guildPartyQuestGuild
	FOREIGN KEY (fkGuild)     
	REFERENCES guild (id) 
	ON UPDATE CASCADE;
	
ALTER TABLE guildPartyQuest
	ADD CONSTRAINT guildPartyQuestParty
	FOREIGN KEY (fkparty)     
	REFERENCES party (id) 
	ON UPDATE CASCADE;

ALTER TABLE guildPartyQuest
	ADD CONSTRAINT guildPartyQuestQuest
	FOREIGN KEY (fkQuest)     
	REFERENCES quest (id) 
	ON UPDATE CASCADE;	
	
INSERT INTO classe (id) VALUE ('Barbarian');
INSERT INTO classe (id) VALUE ('Bard');
INSERT INTO classe (id) VALUE ('Cleric');
INSERT INTO classe (id) VALUE ('Druid');
INSERT INTO classe (id) VALUE ('Fighter');
INSERT INTO classe (id) VALUE ('Monk');
INSERT INTO classe (id) VALUE ('Paladin');
INSERT INTO classe (id) VALUE ('Ranger');
INSERT INTO classe (id) VALUE ('Rogue');
INSERT INTO classe (id) VALUE ('Sorcerer');
INSERT INTO classe (id) VALUE ('Warlock');
INSERT INTO classe (id) VALUE ('Wizard');
	
INSERT INTO race (id) VALUE ('Dragonborn');
INSERT INTO race (id) VALUE ('Dwarf');
INSERT INTO race (id) VALUE ('Elf');
INSERT INTO race (id) VALUE ('Gnome');
INSERT INTO race (id) VALUE ('Halfling');
INSERT INTO race (id) VALUE ('Half-Elf');
INSERT INTO race (id) VALUE ('Half-Orc');
INSERT INTO race (id) VALUE ('Human');
INSERT INTO race (id) VALUE ('Tiefling');

INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (1, 'Retrouver le chat de Yoann', 'Chers aventuriers, mon chat s\'est perdu dans la foret au sud du village. Pourriez-vous le retrouver ?', 30, 30);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (2, 'Du fer pour le forgeron', 'Noble aventuriers, nous sommes manquons de fer dans ma forge. Pourriez-vous allez en chercher dans les mines de la montages du nord.\n P.S. faites attention aux trolls', 50, 50);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (3, 'Une journee aux champs', 'Mon grand-pere vient de se bloquer le dos, pourriez-vous le remplacer pour labourrer mes champs pendant une journee ?', 0, 150);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (4, 'Distribution de tracts', 'Chers camarades, notre parti cherche des benevoles pour distribuer nos tracts pour la prochaine campagne electorale.', 0, 30);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (5, 'Faire le gai', 'Bonjour, j\'en ai marre d\'attendre nuits et jours devant l\'entrée de mon donjon. Pourriez-vous me remplacer pendant une semaine ?', 100, 30);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (6, 'L\'armee des slimes', 'Suite à une erreur dans le dosage, notre fabrique s\'est mise à fabriquer des smlimes a la place de potions. Pourriez-vous s\'il vous plait venir la deslimiser ?', 100, 200);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (7, 'Sauvez la princesse !', 'La princesse Lauris à ete enlevee ! Sauvez-la avant que l\'empire du mal ne nous demande une rancon.', 50000, 10000);
INSERT INTO quest (id, objective, description, rewardGold, rewardExp) VALUE (8, 'Changer de roi', 'Pourriez-vous s\'il vous plait changer de dirigeant. Sa strategie colonialiste empeche mon pays d\'etre prospere. \n Le roi du mal', 30, 30);