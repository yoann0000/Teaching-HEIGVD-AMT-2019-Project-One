package integration;

import Model.Guild;
import datastore.exception.DuplicateKeyException;
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
        String test = "Test_" + System.currentTimeMillis();
        Guild guild = Guild.builder().name(test).reputation(0).members(
                new LinkedList<>()).build();
        guildDAO.create(guild);
    }

}