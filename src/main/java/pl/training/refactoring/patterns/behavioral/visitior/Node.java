package pl.training.refactoring.patterns.behavioral.visitior;

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

    public void accept(Visitor visitor) {
        nodes.forEach(node -> node.accept(visitor));
    }

}
