package marcus_herbert;

public class HSNodeStart extends HSNode implements Runnable{
    public HSNodeStart(int id){
        super(id);
    }

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
                if (!s.substring(0, 5).equals("Start")) {
                    processMessage(s);
                }
            }
        }
    }
}
