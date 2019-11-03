package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuildTest {
    @Test
    void aGuildMustHaveTheRightName(){
        String name = "MaGuilde";
        Guild maGuilde = Guild.builder()
                .name(name)
                .build();
        assertNotNull(maGuilde);
        assertEquals(name, maGuilde.getName());
    }
}