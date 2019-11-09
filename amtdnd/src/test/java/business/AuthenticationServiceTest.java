package business;

import Model.Guild;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticationServiceTest {
    @Test
    void iMustHashPassword(){
                       //$2a$10$3WshE3lpzJFAjC0pAqqWD.5/TfCDgPNJABGp6P9vPYP/jVAKnivZe
                       //$2a$10$eK.ViVbp3hB7cMDYlAw7he7kmqtULbdA04vyDRYY/3MX9FdIjDQ6e
        String result = "$2a$10$y8VooRYElJx3rlbYYHbewup.xvVmUpDSPyuOIyXPORKt6RXn7Iuxa";
        String toBeHashed = "Je suis un PNJ";

        assertEquals(result, new AuthenticationService().hashPassword(toBeHashed));
    }
}