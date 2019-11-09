package Model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class QuestTest {
    @Test
    void aGuildMustHaveTheRightObjective(){
        String objectif = "test";
        Quest maQuete = Quest.builder()
                .objective(objectif)
                .build();
        assertNotNull(maQuete);
        assertEquals(objectif, maQuete.getObjective());
    }

    @Test
    void aGuildMustHaveTheRightDescription(){
        String description = "Ceci est une description";
        Quest maQuete = Quest.builder()
                .objective("test")
                .description(description)
                .build();
        assertNotNull(maQuete);
        assertEquals(description, maQuete.getDescription());
    }

    @Test
    void aGuildMustHaveTheRightAmountOfGold(){
        Quest maQuete = Quest.builder()
                .objective("test")
                .gold(150)
                .build();
        assertNotNull(maQuete);
        assertEquals(150, maQuete.getGold());
    }

    @Test
    void aGuildMustHaveTheRightAmountOfExp(){
        Quest maQuete = Quest.builder()
                .objective("test")
                .exp(100)
                .build();
        assertNotNull(maQuete);
        assertEquals(100, maQuete.getExp());
    }
}