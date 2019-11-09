package Model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PartyTest {
    @Test
    void aPartyMustHaveTheRightName(){
        String name = "MaPartie";
        Party maPartie = Party.builder()
                .name(name)
                .build();
        assertNotNull(maPartie);
        assertEquals(name, maPartie.getName());
    }

    @Test
    void itShouldBePossibleToAddMemberToAParty(){
        Party maPartie = Party.builder()
                .name("MaPartie")
                .members(new LinkedList<>())
                .build();
        Adventurer adventurer = Adventurer.builder()
                .name("aventurier")
                .build();
        assertEquals(0, maPartie.getMembers().size());
        maPartie.addMember(adventurer);
        assertEquals(1, maPartie.getMembers().size());
    }

    @Test
    void itShouldBePossibleToRemoveMemberToAParty(){
        Party maPartie = Party.builder()
                .name("MaPartie")
                .members(new LinkedList<>())
                .build();
        Adventurer adventurer = Adventurer.builder()
                .name("aventurier")
                .build();
        maPartie.addMember(adventurer);
        assertEquals(1, maPartie.getMembers().size());
        maPartie.removeMember(adventurer);
        assertEquals(0, maPartie.getMembers().size());
    }

    @Test
    void itShouldBePossibleToAddReputationToAParty(){
        Party maPartie = Party.builder()
                .name("MaPartie")
                .reputation(0)
                .members(new LinkedList<>())
                .build();
        assertEquals(0, maPartie.getReputation());
        maPartie.addReputation(7);
        assertEquals(7, maPartie.getReputation());
        maPartie.addReputation(234);
        assertEquals(241, maPartie.getReputation());
    }
}