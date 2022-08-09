package pl.training.refactoring.patterns.structural.composite;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Node {

    protected final String name;
    @Setter
    protected List<Node> nodes = new ArrayList<>();

    public void add(Node node) {
        nodes.add(node);
    }

    public void printInfo() {
        nodes.forEach(Node::printInfo);
    }

}
