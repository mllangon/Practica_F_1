import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperimentoGUITest {
    private ExperimentoGUI experimentoGUI;

    @BeforeEach
    void setUp() {
        experimentoGUI = new ExperimentoGUI();
    }

    @Test
    void shouldDisplayFrameOnStart() {
        assertTrue(experimentoGUI.isVisible());
    }


    @AfterEach
    void tearDown() {
        experimentoGUI.dispose();
    }
}