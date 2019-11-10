package datastore;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDataStoreImplementationTest {

    @Test
    void itShouldBePossibleToStoreAndRetrieveAdventurers() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertEquals(0, store.getAllAdventurers().size());

        store.insertAdventurer(Adventurer.builder().name("Yoann").build());
        assertEquals(1, store.getAllAdventurers().size());

        store.insertAdventurer(Adventurer.builder().name("Julien").build());
        assertEquals(2, store.getAllAdventurers().size());
    }

    @Test
    void itShouldNotBePossibleToInsertAnAdventurerWithDuplicateName() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        store.insertAdventurer(Adventurer.builder().name("Julien").build());
        assertThrows(DuplicateKeyException.class, () -> store.insertAdventurer(Adventurer.builder().name("Julien").build()));
    }

    @Test
    void getAllAdventurersShouldReturnObjectClones() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Adventurer julien = Adventurer.builder().name("Julien").build();
        store.insertAdventurer(julien);
        Adventurer julienLoaded = store.loadAdventurerByName("Julien");
        assertNotSame(julien, julienLoaded);
        assertEquals(julien, julienLoaded);
    }

    @Test
    void itShouldBePossibleToUpdateAnAdventurer() throws KeyNotFoundException, DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Adventurer julien = Adventurer.builder()
                .name("Julien")
                .gold(0).build();
        store.insertAdventurer(julien);
        Adventurer updatedJulien = julien.toBuilder().gold(Long.MAX_VALUE).build();
        store.updateAdventurer(updatedJulien);
        Adventurer loaded = store.loadAdventurerByName("Julien");
        assertEquals(Long.MAX_VALUE, loaded.getGold());
        assertEquals(updatedJulien ,loaded);
    }

    @Test
    void itShouldThrowAnExceptionWhenFetchingInvalidAdventurerName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.loadAdventurerByName("spock"));
    }

    @Test
    void itShouldThrowAnExceptionWhenUpdatingInvalidAdventurerName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.updateAdventurer(Adventurer.builder().name("spock").build()));
    }

    @Test
    void itShouldBePossibleToGetAnAdventurerGuild() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Adventurer julien = Adventurer.builder().name("Julien").build();
        store.insertAdventurer(julien);
        assertNull(store.getAdventurerGuild(julien));
        Guild myGuild = Guild.builder().name("MyGuild").members(new LinkedList<>(Arrays.asList(julien))).build();
        store.insertGuild(myGuild);
        store.insertGuild(Guild.builder().name("OtherGuild").members(new LinkedList<>()).build());
        assertEquals(myGuild,  store.getAdventurerGuild(julien));
    }

    @Test
    void itShouldBePossibleToGetAnAdventurerParties() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Adventurer julien = Adventurer.builder().name("Julien").build();
        Adventurer yoann = Adventurer.builder().name("Yoann").build();
        store.insertAdventurer(julien);
        store.insertAdventurer(yoann);
        Party partyJulien = Party.builder().name("partyJulien").members(
                new LinkedList<>(Arrays.asList(julien))).build();
        Party partyHard = Party.builder().name("partyHard").members(
                new LinkedList<>(Arrays.asList(julien, yoann))).build();
        store.insertParty(partyJulien);
        store.insertParty(partyHard);
        assertEquals(1, store.getAdventurerParties(yoann).size());
        assertEquals(2, store.getAdventurerParties(julien).size());
        assertEquals(partyHard, store.getAdventurerParties(yoann).get(0));
    }

    @Test
    void itShouldBePossibleToStoreAndRetrieveGuilds() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertEquals(0, store.getAllGuilds().size());

        store.insertGuild(Guild.builder().name("Guild0").build());
        assertEquals(1, store.getAllGuilds().size());

        store.insertGuild(Guild.builder().name("Guild1").build());
        assertEquals(2, store.getAllGuilds().size());
    }

    @Test
    void itShouldNotBePossibleToInsertAGuildWithDuplicateName() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        store.insertGuild(Guild.builder().name("MyGuild").build());
        assertThrows(DuplicateKeyException.class, () -> store.insertGuild(Guild.builder().name("MyGuild").build()));
    }

    @Test
    void getAllGuildsShouldReturnObjectClones() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Guild guild = Guild.builder().name("MyGuild").build();
        store.insertGuild(guild);
        Guild guildLoaded = store.loadGuildByName("MyGuild");
        assertNotSame(guild, guildLoaded);
        assertEquals(guild, guildLoaded);
    }

    @Test
    void itShouldBePossibleToUpdateAGuild() throws KeyNotFoundException, DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Guild guild = Guild.builder()
                .name("MyGuild")
                .reputation(0).build();
        store.insertGuild(guild);
        Guild updatedGuild = guild.toBuilder().reputation(Integer.MAX_VALUE).build();
        store.updateGuild(updatedGuild);
        Guild loaded = store.loadGuildByName("MyGuild");
        assertEquals(Integer.MAX_VALUE, loaded.getReputation());
        assertEquals(updatedGuild, loaded);
    }

    @Test
    void itShouldThrowAnExceptionWhenFetchingInvalidGuildName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.loadGuildByName("yamiguild"));
    }

    @Test
    void itShouldThrowAnExceptionWhenUpdatingInvalidGuildName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.updateGuild(Guild.builder().name("yamiguild").build()));
    }

    @Test
    void itShouldBePossibleToGetAGuildMembers() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Guild guild = Guild.builder().name("MyGuild").members(new LinkedList<>()).build();
        store.insertGuild(guild);
        assertEquals(0, store.getGuildMembers(guild).size());
        guild.addMember(Adventurer.builder().name("Julien").build());
        guild.addMember(Adventurer.builder().name("Yoann").build());
        store.updateGuild(guild);
        assertEquals(2, store.getGuildMembers(guild).size());
    }

    @Test
    void itShouldBePossibleToStoreAndRetrieveParties() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertEquals(0, store.getAllParties().size());

        store.insertParty(Party.builder().name("MyParty").build());
        assertEquals(1, store.getAllParties().size());

        store.insertParty(Party.builder().name("PartyHard").build());
        assertEquals(2, store.getAllParties().size());
    }

    @Test
    void itShouldNotBePossibleToInsertAPartyWithDuplicateName() throws DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        store.insertParty(Party.builder().name("PartyHard").build());
        assertThrows(DuplicateKeyException.class, () -> store.insertParty(Party.builder().name("PartyHard").build()));
    }

    @Test
    void getAllPartiesShouldReturnObjectClones() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Party party = Party.builder().name("PartyHard").build();
        store.insertParty(party);
        Party partyLoaded = store.loadPartyByName("PartyHard");
        assertNotSame(party, partyLoaded);
        assertEquals(party, partyLoaded);
    }

    @Test
    void itShouldBePossibleToUpdateAParty() throws KeyNotFoundException, DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Party party = Party.builder()
                .name("PartyHard")
                .reputation(0).build();
        store.insertParty(party);
        Party updatedParty = party.toBuilder().reputation(Integer.MAX_VALUE).build();
        store.updateParty(updatedParty);
        Party loaded = store.loadPartyByName("PartyHard");
        assertEquals(Integer.MAX_VALUE, loaded.getReputation());
        assertEquals(updatedParty, loaded);
    }

    @Test
    void itShouldThrowAnExceptionWhenFetchingInvalidPartyName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.loadPartyByName("PartyHard"));
    }

    @Test
    void itShouldThrowAnExceptionWhenUpdatingInvalidPartyName(){
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertThrows(KeyNotFoundException.class, () -> store.updateParty(Party.builder().name("PartyHard").build()));
    }

    @Test
    void itShouldBePossibleToGetAPartyMembers() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Party party = Party.builder().name("PartyHard").members(new LinkedList<>()).build();
        store.insertParty(party);
        assertEquals(0, store.getPartyMembers(party).size());
        party.addMember(Adventurer.builder().name("Julien").build());
        party.addMember(Adventurer.builder().name("Yoann").build());
        store.updateParty(party);
        assertEquals(2, store.getPartyMembers(party).size());
    }

    @Test
    void itShouldBePossibleToStoreAndRetrieveQuests() throws DuplicateKeyException, KeyNotFoundException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        assertEquals(0, store.getAllQuests().size());
        String t1 = "Test1";
        String t2 = "Test2";
        store.insertQuest(Quest.builder().objective(t1).build());
        assertEquals(1, store.getAllQuests().size());
        assertEquals(t1, store.loadQuestByObjective(t1).getObjective());

        store.insertQuest(Quest.builder().objective(t2).build());
        assertEquals(2, store.getAllQuests().size());
        assertEquals(t2, store.loadQuestByObjective(t2).getObjective());
    }

    @Test
    void itShouldNotBePossibleToInsertAQuestWithDuplicateId() throws DuplicateKeyException, KeyNotFoundException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        Quest q1 = Quest.builder().objective("tester").build();
        Quest q2 = Quest.builder().objective("le comportement").build();
        store.insertQuest(q1);
        store.insertQuest(q2);
        assertEquals("tester", store.loadQuestByObjective("tester").getObjective());
        assertEquals("le comportement", store.loadQuestByObjective("le comportement").getObjective());
    }

    @Test
    void getAllQuestsShouldReturnObjectClones() throws KeyNotFoundException, DuplicateKeyException {
        PlayerDataStore store = new PlayerDataStoreImplementation();
        String test = "Test";
        Quest quest = Quest.builder().objective(test).description("Un test").build();
        store.insertQuest(quest);
        quest.setDescription("Ceci est un test");
        Quest questLoaded = store.loadQuestByObjective(test);
        assertNotSame(quest, questLoaded);
        assertEquals(quest, questLoaded);
    }

    @Test
    void itShouldBePossibleToUpdateAQuest() throws KeyNotFoundException, DuplicateKeyException{
        PlayerDataStore store = new PlayerDataStoreImplementation();
        String test = "Test";
        Quest quest = Quest.builder()
                .objective(test)
                .description("Ramasser des champignons")
                .build();
        store.insertQuest(quest);
        Quest updatedQuest= quest.toBuilder().description("Ramasser des bolets").objective(test).build();
        store.updateQuest(updatedQuest);
        Quest loaded = store.loadQuestByObjective(test);
        assertEquals("Ramasser des bolets", loaded.getDescription());
        assertEquals(updatedQuest, loaded);
    }
}