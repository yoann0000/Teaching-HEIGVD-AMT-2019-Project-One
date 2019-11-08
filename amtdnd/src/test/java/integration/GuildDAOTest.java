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

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)

public class GuildDAOTest {

    @EJB
    IGuildDAO guildDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAGuild()  throws DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAGuild() throws DuplicateKeyException, KeyNotFoundException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Guild guildCreated = guildDAO.create(guild);
        Guild guildLoaded = guildDAO.findById(guildCreated.getName());
        assertEquals(guild, guildCreated);
        assertEquals(guild, guildLoaded);
        assertSame(guild, guildCreated);
        assertNotSame(guild, guildLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAGuild() throws DuplicateKeyException, KeyNotFoundException{
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>( )).build();
        Guild guildCreated = guildDAO.create(guild);
        assertEquals(guild, guildCreated);
        guildDAO.deleteById(guildCreated.getName());
        boolean hasThrown = false;
        try{
            Guild guildLoaded = guildDAO.findById(guildCreated.getName());
        }catch (KeyNotFoundException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAGuild() throws DuplicateKeyException, KeyNotFoundException{
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>( )).build();
        Guild guildCreated = guildDAO.create(guild);
        Guild guildModified = guild.toBuilder().reputation(1991).build();
        guildDAO.update(guildModified);
        Guild guildModifiedInDB = guildDAO.findById(guild.getName());
        assertEquals(guildModified, guildModifiedInDB);
        assertNotEquals(guildCreated, guildModifiedInDB);
        assertEquals(1991, guildModifiedInDB.getReputation());
    }
}