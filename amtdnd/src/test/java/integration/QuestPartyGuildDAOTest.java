package integration;

import Model.Guild;
import Model.Party;
import Model.Quest;
import Model.util.PairPG;
import Model.util.PairQG;
import Model.util.PairQP;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class QuestPartyGuildDAOTest {

    @EJB
    IPartyDAO partyDAO;

    @EJB
    IGuildDAO guildDAO;

    @EJB
    IQuestDAO questDAO;

    @EJB
    IQuestPartyGuildDAO questPartyGuildDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToMakeAQuest() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().build().builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        guildDAO.create(guild);
        partyDAO.create(party);
        questDAO.create(quest);
        assertEquals(0, partyDAO.findById(party.getName()).getMembers().size());
        Quest questReturned = questPartyGuildDAO.create(quest, party, guild);
        assertEquals(quest.getObjective(), questReturned.getObjective());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToMakeAQuestMoreThanOneTime() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().build().builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        guildDAO.create(guild);
        partyDAO.create(party);
        questDAO.create(quest);
        questPartyGuildDAO.create(quest, party, guild);
        boolean hasThrown = false;
        try{
            questPartyGuildDAO.create(quest, party, guild);
        }catch (DuplicateKeyException e){
            hasThrown = true;
        }
        assertFalse(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToKnowThePartyWhoDoneAQuestAndOnTheOrderOfWhichGuild() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild1 = Guild.builder().build().builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Guild guild2 = Guild.builder().build().builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party1 = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party2 = Party.builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        guildDAO.create(guild1);
        partyDAO.create(party1);
        guildDAO.create(guild2);
        partyDAO.create(party2);
        questDAO.create(quest);
        questPartyGuildDAO.create(quest, party1, guild1);
        questPartyGuildDAO.create(quest, party1, guild2);
        questPartyGuildDAO.create(quest, party2, guild2);
        List<PairPG> partyGuild = questPartyGuildDAO.getPartiesAndGuildsWhoHasDoneQuest(quest);
        assertEquals(3, partyGuild.size());
        assertEquals(guild1.getName(), partyGuild.get(0).getGuild().getName());
        assertEquals(guild2.getName(), partyGuild.get(1).getGuild().getName());
        assertEquals(guild2.getName(), partyGuild.get(2).getGuild().getName());
        assertEquals(party1.getName(), partyGuild.get(0).getParty().getName());
        assertEquals(party1.getName(), partyGuild.get(1).getParty().getName());
        assertEquals(party2.getName(), partyGuild.get(2).getParty().getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToKnowTheQuestThatHasDoneAPartyAndForWhichGuild() throws KeyNotFoundException, DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Guild guild1 = Guild.builder().build().builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Guild guild2 = Guild.builder().build().builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Quest quest1 = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        Quest quest2 = Quest.builder().objective("Tset_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        partyDAO.create(party);
        guildDAO.create(guild1);
        guildDAO.create(guild2);
        questDAO.create(quest1);
        questDAO.create(quest2);
        questPartyGuildDAO.create(quest1, party, guild1);
        questPartyGuildDAO.create(quest2, party, guild2);
        questPartyGuildDAO.create(quest2, party, guild1);
        List<PairQG> partyGuild = questPartyGuildDAO.getQuestsDoneByParty(party);
        assertEquals(3, partyGuild.size());
        assertEquals(guild1.getName(), partyGuild.get(0).getGuild().getName());
        assertEquals(guild2.getName(), partyGuild.get(1).getGuild().getName());
        assertEquals(guild1.getName(), partyGuild.get(2).getGuild().getName());
        assertEquals(quest1.getObjective(), partyGuild.get(0).getQuest().getObjective());
        assertEquals(quest2.getObjective(), partyGuild.get(1).getQuest().getObjective());
        assertEquals(quest2.getObjective(), partyGuild.get(2).getQuest().getObjective());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToKnowTheGuildWhoEnroledWhichPartyForWhichQuest() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().build().builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party1 = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party party2 = Party.builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Quest quest1 = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        Quest quest2 = Quest.builder().objective("Tset_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        guildDAO.create(guild);
        partyDAO.create(party1);
        partyDAO.create(party2);
        questDAO.create(quest1);
        questDAO.create(quest2);
        questPartyGuildDAO.create(quest1, party1, guild);
        questPartyGuildDAO.create(quest2, party2, guild);
        questPartyGuildDAO.create(quest2, party1, guild);
        List<PairQP> partyGuild = questPartyGuildDAO.getQuestsDoneByGuild(guild);
        assertEquals(3, partyGuild.size());
        assertEquals(party1.getName(), partyGuild.get(0).getParty().getName());
        assertEquals(party2.getName(), partyGuild.get(1).getParty().getName());
        assertEquals(party1.getName(), partyGuild.get(2).getParty().getName());
        assertEquals(quest1.getObjective(), partyGuild.get(0).getQuest().getObjective());
        assertEquals(quest2.getObjective(), partyGuild.get(1).getQuest().getObjective());
        assertEquals(quest2.getObjective(), partyGuild.get(2).getQuest().getObjective());
    }
}