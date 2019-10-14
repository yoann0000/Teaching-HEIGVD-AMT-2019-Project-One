package Model;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode
public class Guild {
    private final String name;
    private final Adventurer leader;
    private List<Adventurer> members;

    public int size(){
        return members.size();
    }
}
