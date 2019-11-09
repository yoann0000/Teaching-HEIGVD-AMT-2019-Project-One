package Model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GuildTest {
    @Test
    void aGuildMustHaveTheRightName(){
        String name = "MaGuilde";
        Guild maGuilde = Guild.builder()
                .name(name)
                .build();
        assertNotNull(maGuilde);
        assertEquals(name, maGuilde.getName());
    }

    @Test
    void itShouldBePossibleToAddMemberToAGuild(){
        Guild maGuilde = Guild.builder()
                .name("MaGuilde")
                .members(new LinkedList<>())
                .build();
        Adventurer adventurer = Adventurer.builder()
                .name("aventurier")
                .build();
        assertEquals(0, maGuilde.getMembers().size());
        maGuilde.addMember(adventurer);
        assertEquals(1, maGuilde.getMembers().size());
    }

    @Test
    void itShouldBePossibleToRemoveMemberToAGuild(){
        Guild maGuilde = Guild.builder()
                .name("MaGuilde")
                .members(new LinkedList<>())
                .build();
        Adventurer adventurer = Adventurer.builder()
                .name("aventurier")
                .build();
        maGuilde.addMember(adventurer);
        assertEquals(1, maGuilde.getMembers().size());
        maGuilde.removeMember(adventurer);
        assertEquals(0, maGuilde.getMembers().size());
    }

    @Test
    void itShouldBePossibleToAddReputationToAGuild(){
        Guild maGuilde = Guild.builder()
                .name("MaGuilde")
                .reputation(0)
                .members(new LinkedList<>())
                .build();
        assertEquals(0, maGuilde.getReputation());
        maGuilde.addReputation(8);
        assertEquals(8, maGuilde.getReputation());
        maGuilde.addReputation(16);
        assertEquals(24, maGuilde.getReputation());
    }
}