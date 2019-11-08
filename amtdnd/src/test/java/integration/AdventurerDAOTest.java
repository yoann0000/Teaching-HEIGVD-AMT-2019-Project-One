package integration;

import Model.Adventurer;
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

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class AdventurerDAOTest {

    @EJB
    IAdventurerDAO adventurerDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAnAdventurer()  throws DuplicateKeyException {
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        adventurerDAO.create(adventurer);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAnAdventurer() throws DuplicateKeyException, KeyNotFoundException {
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").gold(0).spendpoints(0).experience(0).cha(8).wis(8).inte(8).str(8).con(8)
                .dex(8).build();
        Adventurer adventurerCreated = adventurerDAO.create(adventurer);
        Adventurer adventurerLoaded = adventurerDAO.findById(adventurerCreated.getName());
        assertEquals(adventurer, adventurerCreated);
        assertEquals(adventurer, adventurerLoaded);
        assertSame(adventurer, adventurerCreated);
        assertNotSame(adventurer, adventurerLoaded);
        assertEquals(adventurerLoaded.getGold(), 0);
        assertEquals(adventurerLoaded.getCha(), 8);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAnAdventurer() throws DuplicateKeyException, KeyNotFoundException{
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").gold(0).spendpoints(0).experience(0).cha(8).wis(8).inte(8).str(8).con(8)
                .dex(8).build();
        Adventurer adventurerCreated = adventurerDAO.create(adventurer);
        adventurer.toBuilder().gold(0).spendpoints(0).experience(0).cha(8).wis(8).inte(8).str(8).con(8).dex(8).build();
        assertEquals(adventurer, adventurerCreated);
        adventurerDAO.deleteById(adventurerCreated.getName());
        boolean hasThrown = false;
        try{
            Adventurer adventurerLoaded = adventurerDAO.findById(adventurerCreated.getName());
        }catch (KeyNotFoundException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAnAdventurer() throws DuplicateKeyException, KeyNotFoundException{
        Adventurer adventurer = Adventurer.builder().name("Test_" + System.currentTimeMillis()).password("TestPassword")
                .race("Gnome").klass("Wizard").build();
        Adventurer adventurerCreated = adventurerDAO.create(adventurer);
        Adventurer adventurerModified = adventurer.toBuilder().cha(158).build();
        adventurerDAO.update(adventurerModified);
        Adventurer adventurerModifiedInDB = adventurerDAO.findById(adventurer.getName());
        assertEquals(adventurerModified, adventurerModifiedInDB);
        assertNotEquals(adventurerCreated, adventurerModifiedInDB);
        assertEquals(158, adventurerModifiedInDB.getCha());
    }
}