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
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class RaceDAOTest {
    @EJB
    IRaceDAO raceDAO;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToReturnAllRaces() {
        List<String> races = raceDAO.findAll();
        List<String> shouldBe = new LinkedList<>();
        shouldBe.add("Dragonborn");
        shouldBe.add("Dwarf");
        shouldBe.add("Elf");
        shouldBe.add("Gnome");
        shouldBe.add("Half-Elf");
        shouldBe.add("Half-Orc");
        shouldBe.add("Halfling");
        shouldBe.add("Human");
        shouldBe.add("Tiefling");
        for(int i = 0; i < min(races.size(), shouldBe.size()); ++i){
            assertTrue(shouldBe.get(i).equals(races.get(i)));
        }
    }
}