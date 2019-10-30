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
    private final Adventurer leader;
    private List<Adventurer> members;
    private int retputation;

    public int size(){
        return members.size();
    }
}
