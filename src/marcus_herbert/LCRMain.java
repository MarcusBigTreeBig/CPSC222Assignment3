package marcus_herbert;

import java.util.ArrayList;

/**
 * Based on code written by Colton Aarts
 *
 * Tests the LCR algorithm for leader election of distributed systems
 * Creates nodes in a ring, and has a start node that starts the communication of the algorithm so that
 * other nodes continue to send messages.
 */

public class LCRMain {
    public static void main(String[] args) {
        ArrayList<LCRNode> nodes = new ArrayList<>();
        LCRNodeStart nodeStart = new LCRNodeStart(0);
        for(int i = 0; i < 10; i++){
            LCRNode node = new LCRNode(i+1);
            nodes.add(node);
        }

        for(int i = 1; i < 9; i++){
            LCRNode node = nodes.get(i);
            node.setLeft(nodes.get(i-1));
            node.setRight(nodes.get(i+1));
        }
        nodes.get(nodes.size()-1).setRight(nodeStart);
        nodes.get(nodes.size()-1).setLeft(nodes.get(nodes.size()-2));

        nodes.get(0).setRight(nodes.get(1));
        nodes.get(0).setLeft(nodeStart);

        for (LCRNode node : nodes){
            Thread thread = new Thread(node);
            thread.start();
        }

        nodeStart.setLeft(nodes.get(nodes.size()-1));
        nodeStart.setRight(nodes.get(0));

        Thread thread = new Thread(nodeStart);
        thread.start();
    }
}
