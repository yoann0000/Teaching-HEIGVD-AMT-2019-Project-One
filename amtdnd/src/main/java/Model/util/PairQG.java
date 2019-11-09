package Model.util;

import Model.Guild;

import Model.Quest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class PairQG {
    private Quest quest;
    private Guild guild;
}
