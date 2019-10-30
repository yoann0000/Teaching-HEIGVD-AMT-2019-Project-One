package Model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Adventurer {

    public Adventurer(int id, String name, Race race, CharacterClass klass) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.klass = klass;
        this.experience = 0;
        this.gold = 0;
        this.stats = new HashMap<Stats, Integer>();
        stats.put(Stats.STR, 8);
        stats.put(Stats.DEX, 8);
        stats.put(Stats.CON, 8);
        stats.put(Stats.INT, 8);
        stats.put(Stats.WIS, 8);
        stats.put(Stats.CHA, 8);
    }

    public void setStat(Stats statName, int statNum) {
        stats.put(statName, statNum);
    }

    public int getStat(Stats statName) {
        return stats.get(statName);
    }

    public int getLevel() {
        return experience == 0 ? 1 : (int) Math.floor(Math.log10(experience));
    }

    private int id;
    private String name;
    private Race race;
    private CharacterClass klass;
    private int experience;
    private int gold;
    private Map<Stats, Integer> stats;

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
        HUMAN("Human"),
        DWARF("Dwarf"),
        ELF("Elf"),
        GNOME("Gnome"),
        HALFLING("Halfling"),
        DRAGONBORN("Dragonborn"),
        HALFELF("Half-Elf"),
        HALFORC("Half-Orc"),
        TIEFLING("Tiefling");

        Race(String name) {}
    }

    private enum Stats {
        STR("Strength"),
        DEX("Dexterity"),
        CON("Constitution"),
        INT("Intelligence"),
        WIS("Wisdom"),
        CHA("Charisma");

        Stats(String name) {}
    }
}

