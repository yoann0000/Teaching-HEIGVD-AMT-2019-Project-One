package integration;

import Model.Quest;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IQuestDAO extends IDAO<String, Quest> {
    List<Quest> findAll(int offset, int limit);
    int nbOfRecord();
}
