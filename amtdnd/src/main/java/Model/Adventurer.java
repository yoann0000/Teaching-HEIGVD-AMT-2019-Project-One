package Model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Adventurer {
    private String name;
    private String password;
    private String race;
    private String klass;
    private long gold;
    private int str;
    private int dex;
    private int con;
    private int inte;
    private int wis;
    private int cha;
    private int experience;
    private int spendpoints;
    public int getLevel() {
        return experience == 0 ? 1 : (int) Math.floor(Math.log10(experience));
    }
    public int getremainingpoint() {
        return 2 * (getLevel() - 1) - spendpoints;
    }
    public void addGold(int i){
        gold += i;
    }
    public void addExp(int i){
        experience += i;
    }
    public void addStr(){
        str++;
        spendpoints++;
    }
    public void addDex(){
        dex++;
        spendpoints++;
    }
    public void addCon(){
        con++;
        spendpoints++;
    }
    public void addInt(){
        inte++;
        spendpoints++;
    }
    public void addWis(){
        wis++;
        spendpoints++;
    }
    public void addCha(){
        cha++;
        spendpoints++;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

