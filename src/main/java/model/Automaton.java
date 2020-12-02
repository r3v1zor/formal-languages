package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serialization.AutomatonDeserializer;
import serialization.AutomatonSerializer;
import util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(using = AutomatonSerializer.class)
@JsonDeserialize(using = AutomatonDeserializer.class)
public class Automaton {
    private String name;
    private Long weight = 0L;
    private List<String> endNodes;
    private Node startNode;
    private Map<String, Node> nodes;

    public Automaton() {
        nodes = new HashMap<>();
    }

    public Automaton(Node startNode, Map<String, Node> nodes) {
        this.startNode = startNode;
        this.nodes = nodes;
    }

    public Automaton(Node startNode, Map<String, Node> nodes, Long weight) {
        this.startNode = startNode;
        this.nodes = nodes;
        this.weight = weight;
    }

    public Automaton(Node startNode, Map<String, Node> nodes, Long weight, List<String> endNodes) {
        this.startNode = startNode;
        this.nodes = nodes;
        this.weight = weight;
        this.endNodes = endNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
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
        Node node = startNode;

        for (Character ch : str.toCharArray()) {
            String prevValue = node.getPattern();

            node = node.getNextNode(ch.toString());

            if (node == null) {
                result.setLeft(result.getLeft() && endNodes.contains(prevValue));
                return result;
            }

            result.setLeft(true);
            result.setRight(result.getRight() + 1);
        }

        return result;
    }
}