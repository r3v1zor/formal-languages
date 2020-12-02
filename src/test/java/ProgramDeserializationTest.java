import com.fasterxml.jackson.databind.ObjectMapper;
import model.Automaton;
import org.junit.Test;
import util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ProgramDeserializationTest {
    public static final Path folder = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json");
    public static final Path programPath = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/program.txt");

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Automaton> automatons = new ArrayList<>();

        Files.list(folder).forEach(filepath -> {
            try {
                Automaton automaton = mapper.readValue(Files.newInputStream(filepath), Automaton.class);
                automaton.setName(filepath.getFileName().toString().replaceFirst("[.][^.]+$", ""));
                automatons.add(automaton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        String program = Files.readString(programPath).replaceAll("[\n\r]", "");

        Map<String, List<String>> map = new HashMap<>();

        while (true) {
            final String copy = program;

            Integer maxLength = automatons.stream()
                    .map(automaton -> automaton.checkSequence(copy))
                    .filter(Pair::getLeft)
                    .max(Comparator.comparingInt(Pair::getRight))
                    .map(Pair::getRight)
                    .orElse(0);


            Automaton resultAutomaton = automatons.stream()
                    .filter(automaton -> {
                        Pair<Boolean, Integer> pair = automaton.checkSequence(copy);
                        return pair.getLeft() && pair.getRight().equals(maxLength);
                    })
                    .max(Comparator.comparingLong(Automaton::getWeight))
                    .orElse(null);

            if (resultAutomaton == null) {
                break;
            }

            Pair<Boolean, Integer> pair = resultAutomaton.checkSequence(program);

            map.computeIfAbsent(resultAutomaton.getName(), (v) -> new ArrayList<>()).add(program.substring(0, pair.getRight()));
            program = program.substring(pair.getRight());
        }

        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }


    @Test
    public void help() {
        String start = "a";
        String finish = "z";

        List<String> symbols = new ArrayList<>();

        for (int i = start.charAt(0); i <= finish.charAt(0); i++) {
            symbols.add(String.valueOf((char) i));
        }

        for (int i = start.toUpperCase(Locale.ROOT).charAt(0); i <= finish.toUpperCase(Locale.ROOT).charAt(0); i++) {
            symbols.add(String.valueOf((char) i));
        }


        for (int i = 0; i < 10; i++) {
            System.out.print("\"" + i + "\", ");
        }

//        symbols.forEach(symbol -> System.out.println("\"" + symbol + "\": { \"pattern\": \"" + symbol + "\"},"));
//        symbols.forEach(s -> System.out.println("\"" + s + "\": [" + symbols.stream().map(symbol -> "\"" + symbol + "\"").collect(Collectors.joining(", ")) + "],"));
    }
}
