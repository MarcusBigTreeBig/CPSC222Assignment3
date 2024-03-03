package marcus_herbert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HSNode implements Runnable{
    private HSNode left;
    private HSNode right;
    private int id;

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
                System.out.println("Message " + s +" received by " + id);
                right.receiveMessage("Left is " + id);
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