# Ideas
## D&D entities
* Guilds
* Adventuring parties
* Adventurers
* Quests

## Guilds
* name
* leader
* members
 * minimum 10

## Parties
* name
* members
 * 3 to 5
* reputation

## Adventurers
* name
* race
 * Dragonborn
 * Dwarf
 * Elf
 * Gnome
 * Halfling
 * Half-Elf
 * Half-Orc
 * Human
 * Tiefling
* level
 * 1 to 20
* class
 * Barbarian
 * Bard
 * Cleric
 * Druid
 * Fighter
 * Monk
 * Paladin
 * Ranger
 * Rogue
 * Sorcerer
 * Warlock
 * Wizard
* stats
 * strength
 * dexterity
 * constitution
 * intelligence
 * wisdom
 * charisma
 * gold
 
## Quests
* objective
* description
* reward

#Unit tests

## Guild
 * Test that a guild has a leader
 * Test that a guild can't have more than one leader
 * Test the size of the guild (number of adventurer)
 * Test the name of the guild
 * Test if the guild has the minimum of adventurer
 * Test that you can select an adventurer of the guild
 * Test that you can't select an adventurer that is not a member of the guild
 
## Party
 * Test that a party has a leader
 * Test that a party can't have more than one leader
 * Test that a party can't have more than 5 members
 * Test the size of the party (number of adventurer)
 * Test the name of the party
 * Test if the party has the minimum of adventurer
 * Test that you can select an adventurer of the party
 * Test that you can't select an adventurer that is not a member of the party
 
## Adventurer
 * Test that a player has the right name
 * Test that a player has the right race
 * Test that a player has the right level
 * Test that a player has the right class
 * Test that a player has the right stats
 
## Quest
 * Test that a quest has the right description
 * Test that a quest has the right objective
 * Test that a quest has the right reward