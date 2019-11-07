package integration;

import Model.Guild;
import Model.Party;
import datastore.exception.DuplicateKeyException;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)

public class PartyDAOTest {

    @EJB
    IPartyDAO partyDAO;

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAParty()  throws DuplicateKeyException {
        Party guild = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(guild);
    }

}