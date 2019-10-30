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
    Adventurer loadAdventurerByName(String name);
    void updateAdventurer(Adventurer adventurer) throws KeyNotFoundException;
    Guild getAdventurerGuild(Adventurer adventurer) throws KeyNotFoundException;
    List<Party> getAdventurerParties(Adventurer adventurer) throws KeyNotFoundException;
    List<String> getAllClass();
    List<String> getAllRace();

    //Guild section
    List<Guild> getAllGuilds();
    void insertGuild(Guild guild) throws DuplicateKeyException;
    Guild loadGuildByName(String name);
    void updateGuild(Guild guild) throws KeyNotFoundException;
    void deleteGuild(Guild guild) throws KeyNotFoundException;
    List<Adventurer> getGuildMember(Guild guild) throws KeyNotFoundException;

    //Party section
    List<Party> getAllParties();
    void insertParties(Party party) throws DuplicateKeyException;
    Party loadPartyByName(String name);
    void updateParty(Party party) throws KeyNotFoundException;
    void deleteParty(Party party) throws KeyNotFoundException;
    List<Adventurer> getPartyMember(Party party) throws KeyNotFoundException;

    //Quest section
    List<Quest> getAllQuests();
    void insertQuest(Quest quest) throws DuplicateKeyException;
    Quest loadQuestByName(String name);
    void updateQUest(Quest quest) throws KeyNotFoundException;

    //Relation section
    boolean addAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException;
    boolean removeAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException;
    boolean addAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException;
    boolean removeAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException;
    boolean doAQuest(Guild guild, Party party, Quest quest) throws KeyNotFoundException;
}
