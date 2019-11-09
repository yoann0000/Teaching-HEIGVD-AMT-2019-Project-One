package Model.util;

import Model.Guild;
import Model.Quest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

class PairQGTest {
    @Test
    void aPairQGMustHaveTheRightQuestAndGuild(){
        Guild guild = Guild.builder().name("MaGuilde").members(new LinkedList<>()).reputation(0).build();
        Quest quest = Quest.builder().objective("Test").description("Tester").gold(0).exp(0).build();
        PairQG qg = PairQG.builder().guild(guild).quest(quest).build();
        assertEquals(guild, qg.getGuild());
        assertEquals(quest, qg.getQuest());
    }
}