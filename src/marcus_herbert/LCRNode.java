package marcus_herbert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Character.isDigit;

/**
 * Based on code written by Colton Aarts
 *
 * Implements a node for the LCR algorithm of leader election for distributed systems
 * Sends messages containing the largest ID it has seen
 */

public class LCRNode implements Runnable{
    private LCRNode left;
    private LCRNode right;
    private int id;

    private BlockingQueue<String> messages;

    public LCRNode(int id){
        messages = new ArrayBlockingQueue<>(5);
        this.id = id;
    }

    public synchronized void receiveMessage(String mess){
        messages.add(mess);
    }

    /**
     * Takes messages it has received
     * If the ID from the message is larger than the largest one it has seen so far, it keeps that as the largest ID it has seen
     */
    public void run(){
        int largestId;
        int leftId;
        char[] messageArr;
        while(true){
            if(messages.peek() != null){
                String s = null;
                try {
                    s = messages.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Message " + s +" received by " + id);
                leftId = 0;
                messageArr = s.toCharArray();
                for (char c: messageArr) {
                    if (isDigit(c)) {//determine the ID sent by the message
                        leftId = leftId*10+Character.getNumericValue(c);
                    }
                }
                largestId = Math.max(id, leftId);
                right.receiveMessage("Left is " + largestId);
            }
        }
    }

    public void setLeft(LCRNode left) {
        this.left = left;
    }

    public void setRight(LCRNode right) {
        this.right = right;
    }

    public LCRNode getLeft() {
        return left;
    }

    public LCRNode getRight() {
        return right;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlockingQueue<String> getMessages() {
        return messages;
    }

    public void setMessages(BlockingQueue<String> messages) {
        this.messages = messages;
    }
}