package Model;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Guild {
    private final String name;
    private List<Adventurer> members;
    private List<Quest> guildQuests;
    private int reputation;

    public void addMember(Adventurer adventurer){
        members.add(adventurer);
    }
    public void removeMember(Adventurer adventurer){
        members.remove(adventurer);
    }
    public void addReputation(int i){
        reputation += i;
    }
    public void addQuest(Quest quest){
        guildQuests.add(quest);
    }
    public void removeQuest(Quest quest){
        guildQuests.remove(quest);
    }
}
