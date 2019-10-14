package Model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;

@Data
@Builder
@EqualsAndHashCode
public class Adventurer {


    public Map<String, Integer> getStats() {
        return stats;
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

