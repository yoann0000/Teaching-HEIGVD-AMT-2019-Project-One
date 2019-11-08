package integration;

import Model.Party;
import Model.Quest;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class QuestDAOTest {

    @EJB
    IQuestDAO questDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAQuest()  throws DuplicateKeyException {
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        questDAO.create(quest);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAQuest() throws DuplicateKeyException, KeyNotFoundException {
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        Quest questCreated = questDAO.create(quest);
        Quest questLoaded = questDAO.findById(questCreated.getObjective());
        assertEquals(quest, questCreated);
        assertEquals(quest, questLoaded);
        assertSame(quest, questCreated);
        assertNotSame(quest, questLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAQuest() throws DuplicateKeyException, KeyNotFoundException{
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        Quest questCreated = questDAO.create(quest);
        assertEquals(quest, questCreated);
        questDAO.deleteById(questCreated.getObjective());
        boolean hasThrown = false;
        try{
            Quest questLoaded = questDAO.findById(questCreated.getObjective());
        }catch (KeyNotFoundException e){
            hasThrown = true;
        }
        assertTrue(hasThrown);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAQuest() throws DuplicateKeyException, KeyNotFoundException{
        Quest quest = Quest.builder().objective("Test_" + System.currentTimeMillis())
                .description("Tester la connexion").gold(100).exp(100).build();
        Quest questCreated = questDAO.create(quest);
        Quest questModified = quest.toBuilder().exp(120).build();
        questDAO.update(questModified);
        Quest questModifiedInDB = questDAO.findById(quest.getObjective());
        assertEquals(questModified, questModifiedInDB);
        assertNotEquals(questCreated, questModifiedInDB);
        assertEquals(120, questModifiedInDB.getExp());
    }
}