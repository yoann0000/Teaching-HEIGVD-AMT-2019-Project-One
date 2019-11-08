package integration;

import Model.Quest;

import javax.ejb.Local;

@Local
public interface IQuestDAO extends IDAO<String, Quest> {
}
