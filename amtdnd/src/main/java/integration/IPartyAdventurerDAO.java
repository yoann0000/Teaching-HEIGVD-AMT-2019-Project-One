package integration;

import Model.Adventurer;
import Model.Party;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IPartyAdventurerDAO {
    List<Adventurer> findPartyMembersById(String id);
    List<Party> findPlayerPartiesById(String id);
    Adventurer create(Adventurer adventurer, Party party) throws DuplicateKeyException;
    void deleteById(Adventurer adventurer, Party party) throws KeyNotFoundException;
}
