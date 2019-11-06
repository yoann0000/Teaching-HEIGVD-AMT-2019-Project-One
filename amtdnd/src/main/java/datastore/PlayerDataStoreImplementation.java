package datastore;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerDataStoreImplementation implements PlayerDataStore {

    ConcurrentHashMap<String, Adventurer> storeAdventurers = new ConcurrentHashMap<String, Adventurer>();
    ConcurrentHashMap<String, Guild> storeGuilds = new ConcurrentHashMap<String, Guild>();
    ConcurrentHashMap<String, Party> storeParties = new ConcurrentHashMap<String, Party>();
    ConcurrentHashMap<Long, Quest> storeQuests = new ConcurrentHashMap<Long, Quest>();
    List<String> storeClass = new LinkedList<String>();
    List<String> storeRace = new LinkedList<String>();

    long lastQuestId = 0;

    @Override
    public List<Adventurer> getAllAdventurers() {
        return storeAdventurers
                .values()
                .stream()
                .map(adventurer -> {
                    return adventurer.toBuilder().build();
                }).collect(Collectors.toList());
    }

    @Override
    public void insertAdventurer(Adventurer adventurer) throws DuplicateKeyException {
        if(storeAdventurers.get(adventurer.getName()) != null){
            throw new DuplicateKeyException("Adventurer with name " + adventurer.getName() + " already exists.");
        }
        Adventurer clone = adventurer.toBuilder().build();
        storeAdventurers.put(adventurer.getName(), clone);
    }

    @Override
    public Adventurer loadAdventurerByName(String name) throws KeyNotFoundException {
        Adventurer adventurer = storeAdventurers.get(name);
        if(adventurer == null){
            throw new KeyNotFoundException("Could not find adventurer " + name);
        }
        return adventurer.toBuilder().build();
    }

    @Override
    public void updateAdventurer(Adventurer adventurer) throws KeyNotFoundException {
        Adventurer storedAdventurer = storeAdventurers.get(adventurer.getName());
        if(storedAdventurer == null){
            throw new KeyNotFoundException("Could not find adventurer with name " + adventurer.getName());
        }
        Adventurer clone = adventurer.toBuilder().build();
        storeAdventurers.put(adventurer.getName(), clone);
    }

    @Override
    public Guild getAdventurerGuild(Adventurer adventurer) throws KeyNotFoundException {
        if(storeAdventurers.get(adventurer.getName()) == null){
            throw new KeyNotFoundException("Could not find adventurer " + adventurer.getName());
        }
        List<Guild> adventurerGuild = storeGuilds.values()
                .stream()
                .filter(guild -> guild.getMembers().contains(adventurer))
                .limit(1)
                .map(guild -> guild.toBuilder().build())
                .collect(Collectors.toList());
        if(adventurerGuild.size() == 0) return null;
        return adventurerGuild.get(0);
    }

    @Override
    public List<Party> getAdventurerParties(Adventurer adventurer) throws KeyNotFoundException {
        if(storeAdventurers.get(adventurer.getName()) == null){
            throw new KeyNotFoundException("Could not find adventurer " + adventurer.getName());
        }
        List<Party> adventurerParties = storeParties.values()
                .stream()
                .filter(party -> party.getMembers().contains(adventurer))
                .collect(Collectors.toList());
        return adventurerParties
                .stream()
                .map(party -> party.toBuilder().build())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllClass() {
        return new ArrayList<>(storeClass);
    }

    @Override
    public List<String> getAllRace() {
        return storeRace
                .stream()
                .map(String::toString).collect(Collectors.toList());
    }

    @Override
    public List<Guild> getAllGuilds() {
        return storeGuilds
                .values()
                .stream()
                .map(guild -> {
                    return guild.toBuilder().build();
                }).collect(Collectors.toList());
    }

    @Override
    public void insertGuild(Guild guild) throws DuplicateKeyException {
        if(storeGuilds.get(guild.getName()) != null){
            throw new DuplicateKeyException("Guild with name " + guild.getName() + " already exists.");
        }
        Guild clone = guild.toBuilder().build();
        storeGuilds.put(guild.getName(), clone);
    }

    @Override
    public Guild loadGuildByName(String name) throws KeyNotFoundException {
        Guild guild = storeGuilds.get(name);
        if(guild == null){
            throw new KeyNotFoundException("Could not find guild " + name);
        }
        return guild.toBuilder().build();
    }

    @Override
    public void updateGuild(Guild guild) throws KeyNotFoundException {
        Guild storedGuild = storeGuilds.get(guild.getName());
        if(storedGuild == null){
            throw new KeyNotFoundException("Could not find guild " + guild.getName());
        }
        Guild clone = guild.toBuilder().build();
        storeGuilds.put(guild.getName(), clone);
    }

    @Override
    public List<Adventurer> getGuildMembers(Guild guild) throws KeyNotFoundException {
        if(storeGuilds.get(guild.getName()) == null){
            throw new KeyNotFoundException("Could not find guild " + guild.getName());
        }
        return guild.getMembers()
                .stream()
                .map(adventurer -> adventurer.toBuilder().build())
                .collect(Collectors.toList());

    }

    @Override
    public List<Party> getAllParties() {
        return storeParties
                .values()
                .stream()
                .map(party -> {
                    return party.toBuilder().build();
                }).collect(Collectors.toList());
    }

    @Override
    public void insertParty(Party party) throws DuplicateKeyException {
        if(storeParties.get(party.getName()) != null){
            throw new DuplicateKeyException("Party with name " + party.getName() + " already exists.");
        }
        Party clone = party.toBuilder().build();
        storeParties.put(party.getName(), clone);
    }

    @Override
    public Party loadPartyByName(String name) throws KeyNotFoundException {
        Party party = storeParties.get(name);
        if(party == null){
            throw new KeyNotFoundException("Could not find party " + name);
        }
        return party.toBuilder().build();
    }

    @Override
    public void updateParty(Party party) throws KeyNotFoundException {
        Party storedParty = storeParties.get(party.getName());
        if(storedParty == null){
            throw new KeyNotFoundException("Could not find party " + party.getName());
        }
        Party clone = party.toBuilder().build();
        storeParties.put(party.getName(), clone);
    }

    @Override
    public List<Adventurer> getPartyMembers(Party party) throws KeyNotFoundException {
        if(storeParties.get(party.getName()) == null){
            throw new KeyNotFoundException("Could not find party " + party.getName());
        }
        List<Adventurer> partyMembers = party.getMembers();
        return partyMembers
                .stream()
                .map(adventurer -> adventurer.toBuilder( ).build( ))
                .collect(Collectors.toList( ));
    }

    @Override
    public List<Quest> getAllQuests() {
        return storeQuests
                .values()
                .stream()
                .map(quest -> {
                    return quest.toBuilder().build();
                }).collect(Collectors.toList());
    }

    @Override
    public synchronized long insertQuest(Quest quest) throws DuplicateKeyException {
        Quest storedQuest = quest.toBuilder()
                .id(++lastQuestId).build();
        storeQuests.put(lastQuestId, storedQuest);
        return lastQuestId;
    }

    @Override
    public Quest loadQuestById(long id) throws KeyNotFoundException {
        Quest quest = storeQuests.get(id);
        if(quest == null){
            throw new KeyNotFoundException("Could not find quest n°" + id);
        }
        return quest.toBuilder().build();
    }

    public void updateQuest(Quest quest) throws KeyNotFoundException {
        Quest storedQuest = storeQuests.get(quest.getId());
        if(storedQuest == null){
            throw new KeyNotFoundException("Could not find quest n°" + quest.getId());
        }
        Quest clone = quest.toBuilder().build();
        storeQuests.put(quest.getId(), clone);
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
