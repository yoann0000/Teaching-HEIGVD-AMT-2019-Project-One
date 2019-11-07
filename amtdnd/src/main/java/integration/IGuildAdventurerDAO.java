package integration;

import Model.Adventurer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IGuildAdventurerDAO {
    List<Adventurer> findMembersById(String id);
}
