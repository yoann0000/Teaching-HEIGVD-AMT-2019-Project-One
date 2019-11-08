package integration;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IClassDAO {
    List<String> findAll();
}
