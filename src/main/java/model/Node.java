package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {
    private String pattern;

    // connections
    @JsonIgnore
    private Map<String, Node> connections;

    public Node() {
        connections = new HashMap<>();
    }

    public Node(String pattern) {
        this.pattern = pattern;
        connections = new HashMap<>();
    }

    public Node(String pattern, Map<String, Node> connections) {
        this.pattern = pattern;
        this.connections = connections;
    }

    public String getPattern() {
        return pattern;
    }

    public Map<String, Node> getConnections() {
        return connections;
    }

    public Node getNextNode(String pattern) {
        return connections.get(pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(pattern, node.pattern) &&
                Objects.equals(connections, node.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, connections);
    }
}
