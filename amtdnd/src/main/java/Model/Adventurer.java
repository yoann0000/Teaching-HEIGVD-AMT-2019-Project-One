package Model;

import java.util.Map;

public class Adventurer {
    public Adventurer(int id, String name, Race race, CharacterClass klass, int level, int gold, Map<String, Integer> stats) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.klass = klass;
        this.level = level;
        this.gold = gold;
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public CharacterClass getKlass() {
        return klass;
    }

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean setStat(String statName, int statNum) {
        if (!stats.containsKey(statName)) {
            return false;
        } else {
            stats.put(statName, statNum);
            return true;
        }
    }

    private int id;
    private String name;
    private Race race;
    private CharacterClass klass;
    private int level;
    private int gold;
    private Map<String, Integer> stats;

    private enum CharacterClass {
        BARBARIAN("Barbarian"),
        BARD("Bard"),
        CLERIC("Cleric"),
        DRUID("Druid"),
        FIGHTER("Fighter"),
        MONK("Monk"),
        PALADIN("Paladin"),
        RANGER("Ranger"),
        ROGUE("Rogue"),
        SORCERER("Sorcerer"),
        WARLOCK("Warlock"),
        WIZARD("Wizard");


        CharacterClass(String name) {}
    }

    private enum Race {

    }
}

