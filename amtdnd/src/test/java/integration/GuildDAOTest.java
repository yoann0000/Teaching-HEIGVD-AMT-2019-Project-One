package integration;

import Model.Guild;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)

public class GuildDAOTest {

    @EJB
    IGuildDAO guildDAO;

    /*
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(GuildDAO.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

     */

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAGuild()  throws DuplicateKeyException {
        Guild guild = Guild.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
    }

    @Test
    @Transactional(TransactionMode.COMMIT)
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
}