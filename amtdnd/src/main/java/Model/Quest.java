package Model;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Quest {
    private long id;
    private String objective;
    private String description;
    private int gold;
    private int exp;
}
