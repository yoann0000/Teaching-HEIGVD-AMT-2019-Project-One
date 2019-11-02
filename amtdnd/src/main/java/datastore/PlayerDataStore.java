package datastore;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import java.util.List;

public interface PlayerDataStore {

    //Adventurer section
    List<Adventurer> getAllAdventurers();
    void insertAdventurer(Adventurer adventurer) throws DuplicateKeyException;
    Adventurer loadAdventurerByName(String name) throws KeyNotFoundException;
    void updateAdventurer(Adventurer adventurer) throws KeyNotFoundException;
    List<Party> getAdventurerParties(Adventurer adventurer) throws KeyNotFoundException;
    List<String> getAllClass();
    List<String> getAllRace();

    //Guild section
    List<Guild> getAllGuilds();
    void insertGuild(Guild guild) throws DuplicateKeyException;
    Guild loadGuildByName(String name) throws KeyNotFoundException;
    void updateGuild(Guild guild) throws KeyNotFoundException;
    List<Adventurer> getGuildMembers(Guild guild) throws KeyNotFoundException;

    //Party section
    List<Party> getAllParties();
    void insertParty(Party party) throws DuplicateKeyException;
    Party loadPartyByName(String name) throws KeyNotFoundException;
    void updateParty(Party party) throws KeyNotFoundException;
    List<Adventurer> getPartyMembers(Party party) throws KeyNotFoundException;

    //Quest section
    List<Quest> getAllQuests();
    long insertQuest(Quest quest) throws DuplicateKeyException;
    Quest loadQuestById(long id) throws KeyNotFoundException;
    void updateQuest(Quest quest) throws KeyNotFoundException;

    //Relation section
    boolean addAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException;
    boolean removeAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException;
    boolean addAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException;
    boolean removeAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException;
    boolean doAQuest(Guild guild, Party party, Quest quest) throws KeyNotFoundException;
}
