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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class PartyAdventurerDAOTest {

    @EJB
    IPartyDAO partyDAO;

    @EJB
    IAdventurerDAO adventurerDAO;

    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToAsignAPartyToAnAdventurer() throws KeyNotFoundException, DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        assertEquals(0, partyDAO.findById(party.getName()).getMembers().size());
        partyAdventurerDAO.create(adventurer, party);
        Party loadedParty =  partyDAO.findById(party.getName());
        assertEquals(1,loadedParty.getMembers().size());
        assertEquals(adventurer.getName(),loadedParty.getMembers().get(0).getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBePossibleToAsignTwoTimeTheSameAdventurerToAParty() throws DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        partyAdventurerDAO.create(adventurer, party);
        boolean hasThrown = false;
        try{
            partyAdventurerDAO.create(adventurer, party);
        }catch (DuplicateKeyException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToRemoveAnAdventurerFromAParty() throws KeyNotFoundException, DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        partyAdventurerDAO.create(adventurer, party);
        assertEquals(1,partyDAO.findById(party.getName()).getMembers().size());
        partyAdventurerDAO.deleteById(adventurer, party);
        assertEquals(0,partyDAO.findById(party.getName()).getMembers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBePossibleToRemoveAnAdventurerFromAPartyItDoesntBelong() throws KeyNotFoundException, DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        boolean hasThrown = false;
        try{
            partyAdventurerDAO.deleteById(adventurer, party);
        }catch (KeyNotFoundException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToSeeAllAdventurerParties() throws KeyNotFoundException, DuplicateKeyException {
        Party party1 = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party1);
        Party party2 = Party.builder().name("Tset_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party2);
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
        partyAdventurerDAO.create(adventurer, party1);
        partyAdventurerDAO.create(adventurer, party2);
        List<Party> parties = partyAdventurerDAO.findPlayerPartiesById(adventurer.getName());
        assertEquals(2,parties.size());
        assertEquals(party1.getName(),parties.get(0).getName());
        assertEquals(party2.getName(),parties.get(1).getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToSeeAllPartyMembers() throws KeyNotFoundException, DuplicateKeyException {
        Party party = Party.builder().name("Test_" + System.currentTimeMillis()).reputation(0).members(
                new LinkedList<>()).build();
        partyDAO.create(party);
        Adventurer adventurer1 = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer1);
        Adventurer adventurer2 = Adventurer.builder().name("Tset_" + System.currentTimeMillis()).password("TestPassword")
                .race("Elf").klass("Rogue").build();
        adventurerDAO.create(adventurer2);
        partyAdventurerDAO.create(adventurer1, party);
        partyAdventurerDAO.create(adventurer2, party);
        List<Adventurer> members = partyAdventurerDAO.findPartyMembersById(party.getName());
        assertEquals(2,members.size());
        assertEquals(adventurer1.getName(),members.get(0).getName());
        assertEquals(adventurer2.getName(),members.get(1).getName());
    }
}