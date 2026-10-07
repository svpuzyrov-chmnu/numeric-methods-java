package ua.edu.chmnu.ki.c2.math.numeric.series;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputTest {

    private final InputStream originalSystemIn = System.in;

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    void shouldUseDefaultValuesWhenBuilderIsCreated() {
        UserInput userInput = UserInput.builder().build();

        assertAll(
                () -> assertEquals(1.e-3, userInput.getTolerance()),
                () -> assertEquals(0.0, userInput.getX())
        );
    }

    @Test
    void shouldReadToleranceFromStandardInput() {
        System.setIn(new ByteArrayInputStream("0.005\n".getBytes()));

        UserInput userInput = UserInput.builder().withTolerance().build();

        assertEquals(0.005, userInput.getTolerance());
    }

    @Test
    void shouldKeepReadingXUntilValueFallsWithinRange() {
        System.setIn(new ByteArrayInputStream("0\n2\n".getBytes()));

        UserInput userInput = UserInput.builder().withX(1, 3).build();

        assertEquals(2.0, userInput.getX());
    }
}
