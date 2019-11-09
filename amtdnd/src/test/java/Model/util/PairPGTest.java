package Model.util;

import Model.Guild;
import Model.Party;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

class PairPGTest {
    @Test
    void aPairPGMustHaveTheRightPartyAndGuild(){
        Guild guild = Guild.builder().name("MaGuilde").members(new LinkedList<>()).reputation(0).build();
        Party party = Party.builder().name("MaPartie").members(new LinkedList<>()).reputation(0).build();
        PairPG pg = PairPG.builder().guild(guild).party(party).build();
        assertEquals(guild, pg.getGuild());
        assertEquals(party, pg.getParty());
    }
}