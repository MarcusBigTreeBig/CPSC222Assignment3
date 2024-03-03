package marcus_herbert;

import static java.lang.Character.isDigit;

public class LCRNodeStart extends LCRNode implements Runnable{
    public LCRNodeStart(int id){
        super(id);
    }


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
