import org.example.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidateJsonTest {

    private static final String TEST_DIR_PATH = "src/test/resources/";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void runningWithNoArgumentShouldThrowException() {
        assertThatThrownBy(() -> Main.main(new String[]{})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void emptyFileShouldThrowException() {
        assertThatThrownBy(() -> Main.main(new String[]{TEST_DIR_PATH + "empty.json"})).isInstanceOf(IOException.class);
    }

    @Test
    public void notJsonFileShouldThrowException() {
        assertThatThrownBy(() -> Main.main(new String[]{TEST_DIR_PATH + "notJson.json"})).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void manyStatementsWithFirstAsteriskResourceShouldReturnFalse() throws IOException {
        Main.main(new String[]{TEST_DIR_PATH + "manyStatementsWithFirstAsteriskResource.json"});
        assertThat(outContent.toString()).isEqualTo("RESULT: false");
    }

    @Test
    public void manyStatementsWithLastAsteriskResourceShouldReturnFalse() throws IOException {
        Main.main(new String[]{TEST_DIR_PATH + "manyStatementsWithLastAsteriskResource.json"});
        assertThat(outContent.toString()).isEqualTo("RESULT: false");
    }

    @Test
    public void manyStatementsWithNoAsteriskResourceShouldReturnTrue() throws IOException {
        Main.main(new String[]{TEST_DIR_PATH + "manyStatementsWithNoAsteriskResource.json"});
        assertThat(outContent.toString()).isEqualTo("RESULT: true");
    }

    @Test
    public void singleStatementWithNoAsteriskResourceShouldReturnTrue() throws IOException {
        Main.main(new String[]{TEST_DIR_PATH + "singleStatementWithNoAsteriskResource.json"});
        assertThat(outContent.toString()).isEqualTo("RESULT: true");
    }

    @Test
    public void singleStatementWithAsteriskResourceShouldReturnFalse() throws IOException {
        Main.main(new String[]{TEST_DIR_PATH + "singleStatementWithAsteriskResource.json"});
        assertThat(outContent.toString()).isEqualTo("RESULT: false");
    }
}
