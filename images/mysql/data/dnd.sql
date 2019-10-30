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
	name VARCHAR(100) NOT NULL,     
	gold BIGINT UNSIGNED NOT NULL,
	strength SMALLINT UNSIGNED NOT NULL,
	dexterity SMALLINT UNSIGNED NOT NULL,
	constitution SMALLINT UNSIGNED NOT NULL,
	intelligence SMALLINT UNSIGNED NOT NULL,
	wisdom SMALLINT UNSIGNED NOT NULL,
	charisma SMALLINT UNSIGNED NOT NULL,
	fkRace VARCHAR(30) NOT NULL,     
	fkClasse VARCHAR(30) NOT NULL,
	fkGuild INT UNSIGNED,
	guildLeader BOOLEAN,
	CONSTRAINT ID_player PRIMARY KEY (name)
);

CREATE TABLE guild(
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	reputation INT NOT NULL,
	CONSTRAINT ID_guild PRIMARY KEY (id)
);

CREATE TABLE party(
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	reputation INT NOT NULL,
	CONSTRAINT ID_party PRIMARY KEY (id)
);

CREATE TABLE quest(
	id INT UNSIGNED AUTO_INCREMENT,
	objective VARCHAR(50) NOT NULL,
	description VARCHAR(666) NOT NULL,
	rewardGold INT UNSIGNED NOT NULL,
	rewardExp INT UNSIGNED NOT NULL,
	CONSTRAINT ID_quest PRIMARY KEY (id)
);

CREATE TABLE playerParty(
	fkPlayer VARCHAR(100) UNSIGNED NOT NULL,     
	fkParty INT UNSIGNED NOT NULL,     
	leader BOOLEAN NOT NULL,
	CONSTRAINT ID_partyPlayer PRIMARY KEY (fkPlayer, fkParty)
);

CREATE TABLE guildPartyQuest(
	fkGuild INT UNSIGNED NOT NULL,
	fkParty INT UNSIGNED NOT NULL,
	fkQuest INT UNSIGNED NOT NULL,
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
	REFERENCES player (name) 
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