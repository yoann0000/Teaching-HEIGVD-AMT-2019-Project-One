package integration;

import Model.Party;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IPartyDAO extends IDAO<String, Party>{
    List<Party> findAll(int offset, int limit);
    int nbOfRecord();
}
