package integration;

import Model.Guild;
import Model.Party;
import Model.Quest;
import Model.util.PairPG;
import Model.util.PairQG;
import Model.util.PairQP;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IQuestPartyGuildDAO {
    Quest create(Quest quest, Party party, Guild guild) throws DuplicateKeyException;
    List<PairQP> getQuestsDoneByGuild(Guild guild) throws KeyNotFoundException;
    List<PairQG> getQuestsDoneByParty(Party party) throws KeyNotFoundException;
    List<PairPG> getPartiesAndGuildsWhoHasDoneQuest(Quest quest) throws KeyNotFoundException;
}
