package integration;

import Model.Adventurer;
import Model.Guild;
import datastore.exception.KeyNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IGuildAdventurerDAO {
    void addGuildToAdventurer(Adventurer adventurer, Guild guild) throws KeyNotFoundException;
    void removeGuildFromAdventurer(Adventurer adventurer) throws KeyNotFoundException;
    List<Adventurer> findMembersById(String id);
    Guild findAdventurerGuild(String id);
}
