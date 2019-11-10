package integration;

import Model.Adventurer;
import Model.Guild;
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
public class GuildAdventurerDAOTest {

    @EJB
    IGuildDAO guildDAO;

    @EJB
    IAdventurerDAO adventurerDAO;

    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToAsignAGuildToAnAdventurer() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        assertEquals(0, guildDAO.findById(guild.getName()).getMembers().size());
        guildAdventurerDAO.addGuildToAdventurer(adventurer, guild);
        Guild loadedGuild =  guildDAO.findById(guild.getName());
        assertEquals(1,loadedGuild.getMembers().size());
        assertEquals(adventurer.getName(),loadedGuild.getMembers().get(0).getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToChangeAnAdventurersGuild() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild1 = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild1);
        Guild guild2 = Guild.builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild2);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        guildAdventurerDAO.addGuildToAdventurer(adventurer, guild1);
        assertEquals(1,guildDAO.findById(guild1.getName()).getMembers().size());
        assertEquals(0,guildDAO.findById(guild2.getName()).getMembers().size());
        guildAdventurerDAO.addGuildToAdventurer(adventurer, guild2);
        assertEquals(0,guildDAO.findById(guild1.getName()).getMembers().size());
        assertEquals(1,guildDAO.findById(guild2.getName()).getMembers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToRemoveAGuildFromAnAdventurer() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        guildAdventurerDAO.addGuildToAdventurer(adventurer, guild);
        assertEquals(1, guildDAO.findById(guild.getName()).getMembers().size());
        guildAdventurerDAO.removeGuildFromAdventurer(adventurer);
        assertEquals(0,guildDAO.findById(guild.getName()).getMembers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToGetAGuildMembers() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
        Adventurer adventurer1 = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        Adventurer adventurer2 = Adventurer.builder().name("Tset_" + System.currentTimeMillis()).password("TestPassword")
                .race("Elf").klass("Rogue").build();
        adventurerDAO.create(adventurer1);
        adventurerDAO.create(adventurer2);
        guildAdventurerDAO.addGuildToAdventurer(adventurer1, guild);
        guildAdventurerDAO.addGuildToAdventurer(adventurer2, guild);
        List<Adventurer> members = guildAdventurerDAO.findMembersById(guild.getName());
        assertEquals(2, members.size());
        assertEquals(adventurer1.getName(),members.get(0).getName());
        assertEquals(adventurer2.getName(),members.get(1).getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToKnowTheGuildOfAnAdventurer() throws KeyNotFoundException, DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        guildAdventurerDAO.addGuildToAdventurer(adventurer, guild);
        Guild loadedGuild =  guildAdventurerDAO.findAdventurerGuild(adventurer.getName());
        assertEquals(guild.getName(),loadedGuild.getName());
    }
}