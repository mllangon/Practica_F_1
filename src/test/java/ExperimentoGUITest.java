import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginFrameTest {
    private FrameFixture window;


    @Test
    public void shouldAddPopulationAutomatically() {
        // Initialize the Experimento object
        Experimento experimento = new Experimento();

        // Create a new PoblacionBacterias object
        PoblacionBacterias poblacion = new PoblacionBacterias("Test", LocalDate.now(), LocalDate.now().plusDays(30), 1000, 25.0, "Alta", new int[30]);
        poblacion.setFoodPattern("Constant", 100, 50, 200);

        // Add the population to the experiment
        experimento.agregarPoblacion(poblacion);

        // Verify that the population was added correctly
        assertEquals(1, experimento.getPoblaciones().size());
        assertEquals("Test", experimento.getPoblaciones().get(0).getNombre());

        System.out.println("Test passed successfully");
    }
}