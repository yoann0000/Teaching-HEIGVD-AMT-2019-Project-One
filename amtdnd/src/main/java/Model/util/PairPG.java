package Model.util;

import Model.Guild;
import Model.Party;
import Model.Quest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class PairPG {
    private Party party;
    private Guild guild;
}