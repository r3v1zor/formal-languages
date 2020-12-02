package serialization;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Automaton;
import model.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomatonDeserializer extends StdDeserializer<Automaton> {
    public AutomatonDeserializer() {
        this(Automaton.class);
    }

    protected AutomatonDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Automaton deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        String startNodeName = node.get("startNode").get("pattern").asText();
        String endNodes = node.get("endNodes").toString();
        Long weight = node.get("weight").asLong();
        String nodes = node.get("nodes").toString();
        String connections = node.get("connections").toString();

        Map<String, Node> nodesMap = mapper.readValue(nodes, new TypeReference<HashMap<String, Node>>() {});
        Map<String, List<String>> connectionsMap = mapper.readValue(connections, new TypeReference<HashMap<String, List<String>>>() {});
        List<String> endNodesList = mapper.readValue(endNodes, new TypeReference<List<String>>() {});

        Node startNode = nodesMap.get(startNodeName);

        connectionsMap.forEach((key, toNodes) -> {
            Node from = nodesMap.get(key);

            if (from == null) {
                throw new RuntimeException("Connections list includes node: \"" + key + "\" + that doesn't contains in nodes list");
            }

            toNodes.forEach(toNode -> {
                Node to = nodesMap.get(toNode);

                if (to == null) {
                    throw new RuntimeException("Connections list includes node: \"" + toNode + "\" that doesn't contains in nodes list");
                }

                from.getConnections().put(to.getPattern(), to);
            });

        });

        return new Automaton(startNode, nodesMap, weight, endNodesList);
    }
}
