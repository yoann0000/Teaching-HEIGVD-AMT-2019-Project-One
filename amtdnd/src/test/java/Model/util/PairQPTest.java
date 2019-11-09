package Model.util;

import Model.Party;
import Model.Quest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;
class PairQPTest {
    @Test
    void aPairQPGMustHaveTheRightQuestAndParty(){
        Party party = Party.builder().name("MaGuilde").members(new LinkedList<>()).reputation(0).build();
        Quest quest = Quest.builder().objective("Test").description("Tester").gold(0).exp(0).build();
        PairQP qp = PairQP.builder().party(party).quest(quest).build();
        assertEquals(party, qp.getParty());
        assertEquals(quest, qp.getQuest());
    }
}