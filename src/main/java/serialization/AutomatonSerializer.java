package serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.Automaton;
import model.Node;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AutomatonSerializer extends StdSerializer<Automaton> {
    public AutomatonSerializer() {
        this(Automaton.class);
    }

    protected AutomatonSerializer(Class<Automaton> t) {
        super(t);
    }

    @Override
    public void serialize(Automaton value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("startNode", value.getStartNode());
        gen.writeObjectField("nodes", value.getNodes());

        final Map<String, List<String>> connections = new HashMap<>();
        value.getNodes().values().forEach(node -> {
            List<String> connectedNodes = node.getConnections().values().stream()
                    .map(Node::getPattern)
                    .collect(Collectors.toList());

            connections.put(node.getPattern(), connectedNodes);
        });

        gen.writeObjectField("connections", connections);
        gen.writeEndObject();
    }
}
