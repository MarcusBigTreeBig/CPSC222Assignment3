package marcus_herbert;

public class HSNodeStart extends HSNode implements Runnable{
    public HSNodeStart(int id){
        super(id);
    }

    public void run(){
        System.out.println("Start");
        getRight().receiveMessage("Right(zero or one): " + 0 + " is: " + getId() + " Returning (zero or one): " + 0 + " Distance: " + 1 + " Original Sender: " + getId() + ".");
        getLeft().receiveMessage("Right(zero or one): " + 0 + " is: " + getId() + " Returning (zero or one): " + 0 + " Distance: " + 1 + " Original Sender: " + getId() + ".");
        while(true){
            if(getMessages().peek() != null){
                String s = null;
                try {
                    s = getMessages().take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                processMessage(s);
            }
        }
    }
}
