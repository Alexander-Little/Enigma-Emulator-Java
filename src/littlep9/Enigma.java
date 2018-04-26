/*
Alexander Little - alittle5@cnm.edu 
Java - CIS 2235 - Ivonne Nelson
Program 9 Enigma with polymorphism
 */
package littlep9;

/**
 *
 * @author alittle5
 */
public class Enigma {
    private String message;
    private String codedMessage;
    private int key;
    
    public Enigma(){
        message = "";
        codedMessage = "";
        key = 1;
    }
    
    //setters
    //overloaded functions such as this allow behavior to change based on what is required of the class
    //and on what pertinent data is available to be given to the function
    public void setMessage(String m){
        message = m;
        //generate a random key between (1-50)
        key = (int)(Math.random() * 50);
        encode();
    }
    public void setMessage(String m, int k){
        message = m;
        //key = k;
        
        //check that the key is between 1-50
        //if (key <= 50 && key >= 0 || key == 0)
        //{
            key = k;
        //}
        //if not, set to 1
        //else
        //{
        //    key = 1;
        //}
        
        encode();       
    }
    
    public void setCodedMessage(String cm, int k){
        codedMessage = cm;
        key = k;
        
        
        decode();
    }
    
    //getters
    
    public String getCodedMessage(){
        return codedMessage;
    }
    
    public String getDecodedMessage(){
        return message;
    }
    
    public int getKey(){
        return key;
    }
    
    
    private void encode(){
        int charNum;
        String tempMsg = "";
        //Parse through message / change chars to ints / add key to the int value
        for (int i = 0; i < message.length(); i++)
        {
            charNum = (int)message.charAt(i) + key;
        //check if value is > 126
        //if so, wrap to 32 (ex: 127 =>32, 128=>33)
        
            if (charNum > 126)
            { 
               //charNum -= 126;
              // charNum += 32; //(or just charNum -= 94(95 or you miss an index) if you want to be fancy pancy)
                charNum -= 95;
            }
            
        //convert back to char and assemble the coded message   
            tempMsg += (char)charNum;
        }
        codedMessage = tempMsg;
    }
    
    private void decode(){
        //backwards of encode
        int charNum;
        String tempMsg = "";
        for (int i = 0; i < codedMessage.length(); i++)
        {
            charNum = (int)codedMessage.charAt(i) - key;
            
            if (charNum < 32)
            {
                //charNum += 126;
                //charNum -= 32;
                charNum += 95;
            }
            
            tempMsg+= (char)charNum;
        }
        message = tempMsg;
    }
}
