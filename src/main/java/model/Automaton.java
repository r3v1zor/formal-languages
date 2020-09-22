package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serialization.AutomatonDeserializer;
import serialization.AutomatonSerializer;
import util.Pair;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = AutomatonSerializer.class)
@JsonDeserialize(using = AutomatonDeserializer.class)
public class Automaton {
    private Node startNode;
    private Map<String, Node> nodes;

    public Automaton() {
        nodes = new HashMap<>();
    }

    public Automaton(Node startNode, Map<String, Node> nodes) {
        this.startNode = startNode;
        this.nodes = nodes;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public Pair<Boolean, Integer> checkSequence(String sequence) {
        return checkSequence(sequence, 0);
    }

    public Pair<Boolean, Integer> checkSequence(String sequence, int skip) {
        Pair<Boolean, Integer> result = Pair.of(false, 0);

        String str = sequence.substring(skip);

        for (Character ch : str.toCharArray()) {
            Node node = startNode.getNextNode(ch.toString());

            if (node == null) {
                return result;
            }

            result.setLeft(true);
            result.setRight(result.getRight() + 1);
        }

        return result;
    }
}