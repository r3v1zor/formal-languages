import com.fasterxml.jackson.databind.ObjectMapper;
import model.Automaton;
import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DifferentTypesOfDeserializationTest {
    private static final Path config = Paths.get("");
    private static final Path programFilepath = Paths.get("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/securityModifiers.json");
    private final static Path typesJson = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/types.json");
    private final static Path loopCommands = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/loopCommands.json");
    private final static Path switchCommands = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/switchCommands.json");
    private final static Path exceptionCommands = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/exceptionCommands.json");
    private final static Path booleanValues = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/booleanValues.json");
    private final static Path operators = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/operators.json");
    private final static Path signs = Path.of("/home/r3v1zor/Projects/IdeaProjects/formal-languages/src/main/resources/json/signs.json");


    @Test
    public void testSecurityModifiers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(programFilepath), Automaton.class);

        List<String> securityModifiers = List.of("private", "public", "protected");
        securityModifiers.forEach(securityModifier -> Assert.assertEquals(automaton.checkSequence(securityModifier), Pair.of(true, securityModifier.length())));
    }

    @Test
    public void testTypes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(typesJson), Automaton.class);

        List<String> types = List.of("byte", "short", "int", "long", "char", "float", "double", "boolean", "String", "Byte", "Short", "Integer", "Long", "Character", "Float", "Double", "Boolean");
        types.forEach(type -> Assert.assertEquals(automaton.checkSequence(type), Pair.of(true, type.length())));
    }

    @Test
    public void testLoopCommands() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(loopCommands), Automaton.class);

        List<String> loopCommands = List.of("continue", "break", "do", "while", "for");
        loopCommands.forEach(loopCommand -> Assert.assertEquals(automaton.checkSequence(loopCommand), Pair.of(true, loopCommand.length())));
    }

    @Test
    public void testSwitchCommands() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(switchCommands), Automaton.class);

        List<String> switchCommands =  List.of("switch", "case", "break");
        testCommands(automaton, switchCommands);
    }

    @Test
    public void testExceptionCommands() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(exceptionCommands), Automaton.class);

        List<String> exceptionCommands = List.of("try", "catch", "finally", "throw");
        testCommands(automaton, exceptionCommands);
    }

    @Test
    public void testBooleanValues() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(booleanValues), Automaton.class);

        List<String> booleanValues = List.of("true", "false");
        testCommands(automaton, booleanValues);
    }

    @Test
    public void testOperators() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(operators), Automaton.class);

        List<String> operators = List.of("<", ">", "==", "<=", ">=", "&&", "||", "!", "!=", "=", "+", "-", "+=", "-=", "/", "*");
        testCommands(automaton, operators);
    }

    @Test
    public void testSigns() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Automaton automaton = mapper.readValue(Files.newInputStream(signs), Automaton.class);

        List<String> signs = List.of("(", ")", "{", "}", "[", "]", ";", ":", "?", ".", ",");
        testCommands(automaton, signs);
    }

    @Test
    public void getNextSymbols() {
        List<String> words = List.of();
    }

    @Test
    public void generateAutomatons() {

        List<String> securityModifiers = List.of("private", "public", "protected");
        List<String> types = List.of("byte", "short", "int", "long", "char", "float", "double", "boolean", "String", "Byte", "Short", "Integer", "Long", "Character", "Float", "Double", "Boolean");
        List<String> loopCommands = List.of("continue", "break", "do", "while", "for");
        List<String> switchCommands = List.of("switch", "case", "break");
        List<String> exceptionCommands = List.of("try", "catch", "finally", "throw");

        List<String> booleanValues = List.of("true", "false");
        List<String> operators = List.of("<", ">", "==", "<=", ">=", "&&", "||", "!", "!=", "=", "+", "-", "+=", "-=", "/", "*");

        List<String> signs = List.of("(", ")", "{", "}", "[", "]", ";", ":", "?", ".", ",");

        List<String> conditions = List.of("if", "else");

        printJson(getLinks(conditions));
    }

    private void testCommands(Automaton automaton, List<String> commands) {
        commands.forEach(command -> Assert.assertEquals(automaton.checkSequence(command), Pair.of(true, command.length())));
    }

    private Map<String, Set<String>> getLinks(List<String> words) {
        Map<String, Set<String>> symbolToSymbols = new HashMap<>();

        words.forEach(word -> {
            for (int i = 0; i < word.length() - 1; i++) {
                symbolToSymbols
                        .computeIfAbsent(String.valueOf(word.charAt(i)), (v) -> new HashSet<>())
                        .add(String.valueOf(word.charAt(i + 1)));
            }
        });

        System.out.println("\"\": [" + words.stream().map(word -> "\"" + String.valueOf(word.charAt(0)) + "\"").collect(Collectors.joining(",")) + "]");

        return symbolToSymbols;
    }

    public void printJson(Map<String, Set<String>> map) {
        Set<String> set = new HashSet<>(map.keySet());

        set.addAll(map.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()));

        set.forEach(key -> System.out.println("\"" + key + "\": {\"pattern\": \"" + key + "\"},"));
        System.out.println();

        System.out.println("\"\": [" + map.keySet().stream().map(key -> "\"" + key + "\"").collect(Collectors.joining(", ")) + "],");

        map.forEach((key, value) -> System.out.println("\"" + key + "\": [" + value.stream().map(val -> "\"" + val + "\"").collect(Collectors.joining(", ")) + "],"));
    }
}
