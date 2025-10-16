package pl.training.refactoring.patterns.behavioral.visitor;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class Node {

    protected final String name;
    @Setter
    protected List<Node> children = new ArrayList<>();

    public void addChild(Node node) {
        children.add(node);
    }

    public void accept(Visitor visitor) {
        children.forEach(node -> node.accept(visitor));
    }

}
