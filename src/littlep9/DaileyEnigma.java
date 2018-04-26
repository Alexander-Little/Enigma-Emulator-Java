/*
Alexander Little - alittle5@cnm.edu 
Java - CIS 2235 - Ivonne Nelson
Program 9 Enigma with polymorphism
 */
package littlep9;

/**
 *
 * @author Default
 */
public class DaileyEnigma extends Enigma {
    
    private String codedMessage;
    private int key;
    
    public DaileyEnigma() {
        
    }
    @Override
    public void setMessage(String s)
    {
        super.setMessage(s);
        
        codedMessage = super.getCodedMessage();
        
        key = super.getKey();
        
        encode();
    }
    
    //TO ENCODE
    @Override
    public void setMessage(String s, int k){
        
        super.setMessage(s, k);
        //codedMessage = super.getMessage(); //this was in notes, but is it right?
        //message = super.getCodedMessage();//isnt this how it should be?
        //wait just kidding...
        codedMessage = super.getCodedMessage();//isnt this how it should be?
        key = super.getKey();
        encode();
        
    }
    
    //TO DECODE
    @Override
    public void setCodedMessage(String s, int k){
        codedMessage = s;
        key = k;
        decode();
        super.setCodedMessage(codedMessage, key);
        
    }
    
    @Override
        public String getCodedMessage(){
        return codedMessage;
    }
    
   // public String getDecodedMessage(){
   //     return message;
   // }
    
    @Override
    public int getKey(){
        return key;
    }
      
    private void encode(){
        //string with random chars added in
        StringBuilder randomStr = new StringBuilder();
        String codedTemp = "";
        
//A)        //add random character after each character in the message
        for (int i = 0; i < codedMessage.length(); i++){
            int randcii;
            randcii = (int)(Math.random() * 94) + 31; //creates a random number between 32-126
            char t = (char)randcii; 
            //char t = 'X'; //for EZPZ checking/visualising
            randomStr.append(codedMessage.charAt(i));
            randomStr.append(t);
        }
        
//B)       //Break into groups = //length of group is key % 4 + 2;
           //example: if key = 41 then groups are 3 characters long
        //extra variables because I want to be extra careful! 
        int grpSize = 0; //size of groups
        grpSize = (key % 4) + 2;
        int grpCnt = 0; //# of groups
        grpCnt = randomStr.length() / grpSize;
        StringBuilder temp;
        StringBuilder funkedMess = new StringBuilder();
        
//C)      //Reverse string by "grpSize" characters at a time 
        //random char string taken and reversed in groups of (key % 4) + 2;  
        int increment = 0;
        for (int x = 0; x < grpCnt; x++){
            temp = new StringBuilder();
            //taking substring and reversing it
            temp.append(randomStr.substring(increment, increment + grpSize)).reverse(); 
            increment += grpSize; //Increment increment by increments of grpSize :3
            //add funked up substring to the 'big picture' funky message
            funkedMess.append(temp);
        }
        //end of string (that doesnt add up to the length) taken, reversed and thrown on the end
        temp = new StringBuilder();
        temp.append(randomStr.substring(increment)).reverse();
        funkedMess.append(temp);       
        //funked up string replaces codedMessage
        codedMessage = funkedMess.toString();
        //codedMessage = randomStr.toString(); //easy check to see step A worked
    }
    

    private void decode(){
        // It's reverse on backwards day! 
        StringBuilder deFunkedMess = new StringBuilder();
        StringBuilder temp;
        int grpCnt; 
        int grpSize;
        
        grpSize = (key % 4) + 2;
        grpCnt = (codedMessage.length()) / grpSize;
        //random char string taken and reversed in groups of (key % 4) + 2;  
        int increment = 0;
        for (int x = 0; x < grpCnt; x++){
            temp = new StringBuilder();
            //taking substring and reversing it
            temp.append(codedMessage.substring(increment, increment + grpSize)).reverse(); 
            increment += grpSize; //Increment increment by increments of grpSize :3
            //add funked down substring to the 'big picture' deFunky message
            deFunkedMess.append(temp);
        }
        //end of string (that doesnt add up to the length) taken, reversed and thrown on the end
        temp = new StringBuilder();
        temp.append(codedMessage.substring(increment)).reverse();
        deFunkedMess.append(temp.toString());
        //now deFunkedMess is only partially funked (every other char is still random)
        //but it's no longer hit by a tornado as well.            
        //so lastly, need to essentially "deFunkedMess.replace(0, 1, "");" through the whole message  
        for (int w = 1; w < deFunkedMess.length(); w++){
            //temp.append(deFunkedMess.charAt(w-1));
            deFunkedMess.replace(w, w+1, "");
        }      
        codedMessage = deFunkedMess.toString();       
      }
}