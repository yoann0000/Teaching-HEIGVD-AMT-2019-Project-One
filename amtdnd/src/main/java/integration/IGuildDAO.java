package integration;

import Model.Guild;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IGuildDAO extends IDAO<String, Guild>  {
    List<Guild> findAll(int offset, int limit);
    int nbOfRecord();
}
