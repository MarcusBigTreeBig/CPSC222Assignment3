package marcus_herbert;

public class HSNodeStart extends HSNode implements Runnable{
    public HSNodeStart(int id){
        super(id);
    }

    public void run(){
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
                getRight().receiveMessage("Left is " + getId());
            }
        }
    }
}
