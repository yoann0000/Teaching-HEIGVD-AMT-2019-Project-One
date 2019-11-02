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
    private int id;
    private String name;
    private String race;
    private String klass;
    private int experience;
    private long gold;
    private int str;
    private int dex;
    private int con;
    private int inte;
    private int wis;
    private int cha;
    private Guild guild;

    public int getLevel() {
        return experience == 0 ? 1 : (int) Math.floor(Math.log10(experience));
    }
}

