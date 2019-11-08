package integration;

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

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class ClassDAOTest {

    @EJB
    IClassDAO classDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToReturnAllClasses() {
        List<String> classes = classDAO.findAll();
        List<String> shouldBe = new LinkedList<>();
        shouldBe.add("Barbarian");
        shouldBe.add("Bard");
        shouldBe.add("Cleric");
        shouldBe.add("Druid");
        shouldBe.add("Fighter");
        shouldBe.add("Monk");
        shouldBe.add("Paladin");
        shouldBe.add("Ranger");
        shouldBe.add("Rogue");
        shouldBe.add("Sorcerer");
        shouldBe.add("Warlock");
        shouldBe.add("Wizard");
        for(int i = 0; i < min(classes.size(), shouldBe.size()); ++i){
            assertTrue(shouldBe.get(i).equals(classes.get(i)));
        }
    }
}