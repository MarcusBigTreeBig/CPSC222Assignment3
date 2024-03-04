package marcus_herbert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Character.isDigit;

public class HSNode implements Runnable{
    private HSNode left;
    private HSNode right;
    private int id;
    private int largestId;

    private BlockingQueue<String> messages;

    public HSNode(int id){
        messages = new ArrayBlockingQueue<>(5);
        this.id = id;
    }

    public synchronized void receiveMessage(String mess){
        messages.add(mess);
    }

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

    public void processMessage (String s) {
        System.out.println("Message " + s +" received by " + id);
        int isRight = 0;
        int returning = 0;
        int distance = 0;
        int originalSender = 0;
        boolean sendLeft = false;
        boolean sendRight = false;
        int i = 0;
        char[] arr = s.toCharArray();
        while (!isDigit(arr[i])) {
            i++;
        }
        while (isDigit(arr[i])) {
            isRight = isRight*10+Character.getNumericValue(arr[i]);
            i++;
        }
        while (!isDigit(arr[i])) {
            i++;
        }
        while (isDigit(arr[i])) {
            largestId = Math.max(largestId, largestId*10+Character.getNumericValue(arr[i]));
            i++;
        }
        while (!isDigit(arr[i])) {
            i++;
        }
        while (isDigit(arr[i])) {
            returning = returning*10+Character.getNumericValue(arr[i]);
            i++;
        }
        while (!isDigit(arr[i])) {
            i++;
        }
        while (isDigit(arr[i])) {
            distance = distance*10+Character.getNumericValue(arr[i]);
            i++;
        }
        while (!isDigit(arr[i])) {
            i++;
        }
        while (isDigit(arr[i])) {
            originalSender = originalSender*10+Character.getNumericValue(arr[i]);
            i++;
        }
        if (returning == 1) {
            if (originalSender == id) {
                sendLeft = true;
                sendRight = true;
                distance++;
                returning = 0;
                originalSender = id;
            }else{
                if (isRight == 1) {
                    sendLeft = true;
                }else{
                    sendRight = true;
                }
                distance++;
            }
        }
        else{
            if (distance == 0) {
                returning = 1;
                if (isRight == 1) {
                    sendRight = true;
                }else{
                    sendLeft = true;
                }
            }
            else{
                if (isRight == 1) {
                    sendLeft = true;
                }else{
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