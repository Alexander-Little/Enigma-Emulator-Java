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
public class ShiftyEnigma extends Enigma {
    //private final String[] symbols = {")","!","@","#","$","%","^","&","*","("};
    private final char[] symbols = {')','!','@','#','$','%','^','&','*','('};
    private int key;
    private String codedMessage;
    
    
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
        codedMessage = super.getCodedMessage();
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
        
        Integer tripNum = 0;
        //int tripNum;
        StringBuilder tripStr = new StringBuilder();
       // String tripStr = "";
        //String tempMsg = "";
        StringBuilder tempMsg = new StringBuilder();
        int tempNum = 0;
        
       for (int i = 0; i < codedMessage.length(); i++){
           //go through each character, make into a triplet of ints
           tripNum = 1000 - ((int)(codedMessage.charAt(i)));
           //tripStr.append(tripNum);
           tripStr.replace(0, 2, tripNum.toString());
           
           for (int j = 0; j < 3; j++){
               
           //go through triplet of ints, make each number correspond to symbols array
               String target = "" + tripStr.charAt(j);
               tempNum = Integer.parseInt(target);
           //add these to the tempMessage
             tempMsg.append(symbols[tempNum]);
           }
       } 
      // tempMsg.append(tempNum);
       codedMessage = tempMsg.toString(); //send the newly encoded message to the coded message

    }
    

    private void decode(){
        String decodedTemp = "";
        StringBuilder symbolStr = new StringBuilder();
        StringBuilder numberStr = new StringBuilder();
//a)        //get string of symbols (unnecessary, but I like to see what's happening)
        for (int i = 0; i < codedMessage.length(); i++){
            symbolStr.append(codedMessage.charAt(i));
        }
//b)       //turn string of symbols into string of corresponding ints
        for (int j = 0; j < symbolStr.length(); j++){
            
            for (int x = 0; x < 10; x++){
                if (symbolStr.charAt(j) == symbols[x]){
                    numberStr.append(x);
                }
            }
        }
//c)    //Turn string of ints back into their encoded asciis
        for (int k = 0; k < numberStr.length(); k+=3){ //go by threes..
           int triplet = 0; 
           triplet = Integer.parseInt(numberStr.substring(k, k+3)); //take next three ints
           triplet = (triplet - 1000) * (-1); //subtract 1000 and flip from negative to positive
           decodedTemp += (char)triplet; //insert ascii char of new value into the decoded message
        }
        codedMessage = decodedTemp;  
       // codedMessage = //numberStr.toString();//symbolStr.toString();//quick test returns
    }
}