package marcus_herbert;

import static java.lang.Character.isDigit;

/**
 * Based on code written by Colton Aarts
 *
 * Implements a start node for the LCR algorithm of leader election for distributed systems
 * Sends messages containing the largest ID it has seen
 * At the start, sends an initial message to start the algorithm.
 */

public class LCRNodeStart extends LCRNode implements Runnable{
    public LCRNodeStart(int id){
        super(id);
    }

    /**
     * If algorithm hasn't started yet, sends an initial message to the node on it's left.
     *
     * Takes messages it has received
     * If the ID from the message is larger than the largest one it has seen so far, it keeps that as the largest ID it has seen
     */
    public void run(){
        int largestId;
        int leftId;
        char[] messageArr;
        System.out.println("Start");
        getRight().receiveMessage("Left is " + getId());
        while(true){
            if(getMessages().peek() != null){
                String s = null;
                try {
                    s = getMessages().take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Message " + s +" received by " + getId());
                leftId = 0;
                messageArr = s.toCharArray();
                for (char c: messageArr) {
                    if (isDigit(c)) {
                        leftId = leftId*10+Character.getNumericValue(c);
                    }
                }
                largestId = Math.max(getId(), leftId);
                getRight().receiveMessage("Left is " + largestId);
            }
        }
    }
}
