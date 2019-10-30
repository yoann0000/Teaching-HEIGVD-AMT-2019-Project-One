package datastore;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerDataStoreImplementation implements PlayerDataStore {

    public static final boolean INCLUDE_DELETED = true;

    ConcurrentHashMap<String, Adventurer> storeAdventurers = new ConcurrentHashMap<String, Adventurer>();
    ConcurrentHashMap<Long, Guild> storeGuilds = new ConcurrentHashMap<Long, Guild>();
    ConcurrentHashMap<Long, Party> storeParties = new ConcurrentHashMap<Long, Party>();
    ConcurrentHashMap<Long, Quest> storeQuests = new ConcurrentHashMap<Long, Quest>();

    long lastAdventurerId = 0;
    long lastGuildId = 0;
    long lastPartyId = 0;
    long lastQuestId = 0;

    @Override
    public List<Adventurer> getAllAdventurers() {
        return storeAdventurers
                .values()
                .stream()
                .map(adventurer -> {
                    Adventurer clone = adventurer.toBuilder().build();
                    return clone;
                }).collect(Collectors.toList());
    }

    @Override
    public void insertAdventurer(Adventurer adventurer) throws DuplicateKeyException {
        if(storeAdventurers.get(adventurer.getName()) != null){
            throw new DuplicateKeyException("Adventurer with id " + adventurer.getName() + " already exists.");
        }
        Adventurer clone = adventurer.toBuilder().build();
        storeAdventurers.put(adventurer.getName(), clone);
    }

    @Override
    public Adventurer loadAdventurerByName(String name) {
        return null;
    }

    public void updateAdventurer(Adventurer adventurer) throws KeyNotFoundException {

    }

    public Guild getAdventurerGuild(Adventurer adventurer) throws KeyNotFoundException {
        return null;
    }

    public List<Party> getAdventurerParties(Adventurer adventurer) throws KeyNotFoundException {
        return null;
    }

    public List<String> getAllClass() {
        return null;
    }

    public List<String> getAllRace() {
        return null;
    }

    public List<Guild> getAllGuilds() {
        return null;
    }

    public void insertGuild(Guild guild) throws DuplicateKeyException {

    }

    public Guild loadGuildByName(String name) {
        return null;
    }

    public void updateGuild(Guild guild) throws KeyNotFoundException {

    }

    public void deleteGuild(Guild guild) throws KeyNotFoundException {

    }

    public List<Adventurer> getGuildMember(Guild guild) throws KeyNotFoundException {
        return null;
    }

    public List<Party> getAllParties() {
        return null;
    }

    public void insertParties(Party party) throws DuplicateKeyException {

    }

    public Party loadPartyByName(String name) {
        return null;
    }

    public void updateParty(Party party) throws KeyNotFoundException {

    }

    public void deleteParty(Party party) throws KeyNotFoundException {

    }

    public List<Adventurer> getPartyMember(Party party) throws KeyNotFoundException {
        return null;
    }

    public List<Quest> getAllQuests() {
        return null;
    }

    public void insertQuest(Quest quest) throws DuplicateKeyException {

    }

    public Quest loadQuestByName(String name) {
        return null;
    }

    public void updateQUest(Quest quest) throws KeyNotFoundException {

    }

    public boolean addAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException {
        return false;
    }

    public boolean removeAdventurerToGuild(Adventurer adventurer, Guild guild) throws KeyNotFoundException {
        return false;
    }

    public boolean addAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException {
        return false;
    }

    public boolean removeAdventurerToParty(Adventurer adventurer, Party party) throws KeyNotFoundException {
        return false;
    }

    public boolean doAQuest(Guild guild, Party party, Quest quest) throws KeyNotFoundException {
        return false;
    }
}
