package marcus_herbert;

import java.util.ArrayList;

public class HSMain {
    public static void main(String[] args) {
        ArrayList<HSNode> nodes = new ArrayList<>();
        HSNodeStart nodeStart = new HSNodeStart(0);
        for(int i = 0; i < 10; i++){
            HSNode node = new HSNode(i+1);
            nodes.add(node);
        }

        for(int i = 1; i < 9; i++){
            HSNode node = nodes.get(i);
            node.setLeft(nodes.get(i-1));
            node.setRight(nodes.get(i+1));
        }
        nodes.get(nodes.size()-1).setRight(nodeStart);
        nodes.get(nodes.size()-1).setLeft(nodes.get(nodes.size()-2));

        nodes.get(0).setRight(nodes.get(1));
        nodes.get(0).setLeft(nodeStart);

        for (HSNode node : nodes){
            Thread thread = new Thread(node);
            thread.start();
        }

        nodeStart.setLeft(nodes.get(nodes.size()-1));
        nodeStart.setRight(nodes.get(0));

        Thread thread = new Thread(nodeStart);
        thread.start();
    }
}