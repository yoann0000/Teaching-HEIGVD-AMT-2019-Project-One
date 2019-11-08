package integration;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IRaceDAO {
    List<String> findAll();
}
