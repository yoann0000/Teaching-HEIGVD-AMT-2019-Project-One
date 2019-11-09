package Model.util;

import Model.Party;
import Model.Quest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class PairQP {
    private Quest quest;
    private Party party;
}
