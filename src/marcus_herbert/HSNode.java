package marcus_herbert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Character.isDigit;

/**
 * Based on code written by Colton Aarts
 *
 * Implements a node for the HS algorithm of leader election for distributed systems
 * Sends messages containing the largest ID it has seen, and other information needed for the algorithm to work
 */

public class HSNode implements Runnable{
    private HSNode left;
    private HSNode right;
    private int id;
    private int largestId;

    private BlockingQueue<String> messages;

    public HSNode(int id){
        messages = new ArrayBlockingQueue<>(20);
        this.id = id;
    }

    public synchronized void receiveMessage(String mess){
        messages.add(mess);
    }

    /**
     * takes messages the node has been sent and processes them
     */
    public void run(){
        while(true){
            if(messages.peek() != null){
                String s = null;
                try {
                    s = messages.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                processMessage(s);
            }
        }
    }

    /**
     * takes a message the node has received
     * pulls all the information out of the message as integers
     * determines what message needs to be sent to neighbour nodes
     *
     * @param s
     */
    public void processMessage (String s) {
        System.out.println("Message " + s +" received by " + id);
        int isRight = 0;
        int returning = 0;
        int distance = 0;
        int originalSender = 0;
        int tempId = 0;
        boolean sendLeft = false;
        boolean sendRight = false;
        int i = 0;
        char[] arr = s.toCharArray();
        if (s.substring(0, 5).equals("Start")) {//if this message is the starting message
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine which direction the message was sent from
                isRight = isRight*10+Character.getNumericValue(arr[i]);
                i++;
            }
            if (isRight == 1) {//continue the start message in the same direction
                right.receiveMessage("Start 1.");
            }else{
                right.receiveMessage("Start 0.");
            }
            getRight().receiveMessage("Right(zero or one): " + 0 + " is: " + getId() + " Returning (zero or one): " + 0 + " Distance: " + 1 + " Original Sender: " + getId() + ".");
            getLeft().receiveMessage("Right(zero or one): " + 0 + " is: " + getId() + " Returning (zero or one): " + 0 + " Distance: " + 1 + " Original Sender: " + getId() + ".");
        }else { //if not the start message
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine which direction the message was sent from
                isRight = isRight*10+Character.getNumericValue(arr[i]);
                i++;
            }
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine the id sent in the message
                tempId = tempId*10+Character.getNumericValue(arr[i]);
                i++;
            }
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine if the message is going forward or returning
                returning = returning*10+Character.getNumericValue(arr[i]);
                i++;
            }
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine the distance the node has to travel or has travelled
                distance = distance*10+Character.getNumericValue(arr[i]);
                i++;
            }
            while (!isDigit(arr[i])) {
                i++;
            }
            while (isDigit(arr[i])) {//determine the original sender of the message
                originalSender = originalSender*10+Character.getNumericValue(arr[i]);
                i++;
            }
            largestId = Math.max(tempId, largestId);
            if (returning == 1) {
                if (originalSender == id) {
                    //if the sender sees it's own message returned back to it, it resends it with a larger distance
                    sendLeft = true;
                    sendRight = true;
                    distance++;
                    returning = 0;
                    originalSender = id;
                } else {
                    //if a node sees a returning message, it passes it on
                    if (isRight == 1) {
                        sendLeft = true;
                    } else {
                        sendRight = true;
                    }
                    distance++;
                }
            } else {
                if (distance == 0) {
                    //if the message has run the distance it should, start returning it
                    returning = 1;
                    if (isRight == 1) {
                        sendRight = true;
                    } else {
                        sendLeft = true;
                    }
                } else {
                    //continue the message on until it reaches the distance
                    if (isRight == 1) {
                        sendLeft = true;
                    } else {
                        sendRight = true;
                    }
                    distance--;
                }
            }
            if (sendLeft) {
                right.receiveMessage("Right(zero or one): " + 0 + " is: " + largestId + " Returning (zero or one): " + returning + " Distance: " + distance + " Original Sender: " + originalSender + ".");
            }
            if (sendRight) {
                left.receiveMessage("Right(zero or one): " + 1 + " is: " + largestId + " Returning (zero or one): " + returning + " Distance: " + distance + " Original Sender: " + originalSender + ".");
            }
        }
    }

    public void setLeft(HSNode left) {
        this.left = left;
    }

    public void setRight(HSNode right) {
        this.right = right;
    }

    public HSNode getLeft() {
        return left;
    }

    public HSNode getRight() {
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