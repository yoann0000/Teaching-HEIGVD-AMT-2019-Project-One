package Model;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Party {
    private final String name;
    private List<Adventurer> members;
    private int retputation;

    public int size(){
        return members.size();
    }
    public void addMember(Adventurer adventurer){
        members.add(adventurer);
    }
    public void removeMember(Adventurer adventurer){
        members.remove(adventurer);
    }
}
