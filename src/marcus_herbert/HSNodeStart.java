package marcus_herbert;

/**
 * Based on code written by Colton Aarts
 *
 * Implements a start node for the HS algorithm of leader election for distributed systems
 * Sends messages containing the largest ID it has seen, and other information needed for the algorithm to work
 *
 * If the algorithm has not started, sends initial messages to sstart the algorithm
 */

public class HSNodeStart extends HSNode implements Runnable{
    public HSNodeStart(int id){
        super(id);
    }

    /**
     * if the algorithm has not yet started, send an initial message to the left and right
     *
     * takes messages the node has been sent and processes them
     */
    public void run(){
        System.out.println("Start");
        processMessage("Start 0.");
        processMessage("Start 1.");
        while(true){
            if(getMessages().peek() != null){
                String s = null;
                try {
                    s = getMessages().take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!s.substring(0, 5).equals("Start")) {//discards starting messages when they return to this node
                    processMessage(s);
                }
            }
        }
    }
}
