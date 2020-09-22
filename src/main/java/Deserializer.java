import com.fasterxml.jackson.databind.ObjectMapper;
import model.Automaton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deserializer {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Automaton automaton = mapper.readValue(Files.newInputStream(Paths.get("")), Automaton.class);
            System.out.println(automaton.checkSequence("f 123"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
