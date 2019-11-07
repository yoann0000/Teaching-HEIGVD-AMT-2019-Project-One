package integration;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
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

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAndRetrieveAParty() throws DuplicateKeyException, KeyNotFoundException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        Party partyCreated = partyDAO.create(party);
        Party partyLoaded = partyDAO.findById(partyCreated.getName());
        assertEquals(party, partyCreated);
        assertEquals(party, partyLoaded);
        assertSame(party, partyCreated);
        assertNotSame(party, partyLoaded);
    }

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToDeleteAGuild() throws DuplicateKeyException, KeyNotFoundException{
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<Adventurer>()).build();
        Party partyCreated = partyDAO.create(party);
        assertEquals(party, partyCreated);
        partyDAO.deleteById(partyCreated.getName());
        boolean hasThrown = false;
        try{
            Party partyLoaded = partyDAO.findById(partyCreated.getName());
        }catch (KeyNotFoundException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToUpdateAGuild() throws DuplicateKeyException, KeyNotFoundException{
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<Adventurer>()).build();
        Party partyCreated = partyDAO.create(party);
        Party partyModified = party.toBuilder().reputation(1991).build();
        partyDAO.update(partyModified);
        Party partyModifiedInDB = partyDAO.findById(party.getName());
        assertEquals(partyModified, partyModifiedInDB);
        assertNotEquals(partyCreated, partyModifiedInDB);
        assertEquals(1991, partyModifiedInDB.getReputation());
    }
}