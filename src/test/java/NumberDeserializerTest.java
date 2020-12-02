import com.fasterxml.jackson.databind.ObjectMapper;
import model.Automaton;
import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NumberDeserializerTest {
    private static final Path config = Paths.get("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/number.json");

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(config), Automaton.class);


        Assert.assertEquals(automaton.checkSequence("123f"), Pair.of(true, 3));
        Assert.assertEquals(automaton.checkSequence("f123"), Pair.of(false, 0));
        Assert.assertEquals(automaton.checkSequence("f123", 1), Pair.of(true, 3));
    }

    @Test
    public void testDoubleValue() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(config), Automaton.class);

        System.out.println(automaton.checkSequence("2.2e5"));
    }

}
