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
public class PrimeShiftEnigma extends Enigma {

    private final int[] primes = {37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113};//19
    private final int[] kPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};//15
    private boolean keyIsPrime;
    private String codedMessage;
    private int key;

    public PrimeShiftEnigma() {
        // codedMessage = super.getCodedMessage();
        //  message = super.getDecodedMessage();
        // key = super.getKey();
    }

    @Override
    public void setMessage(String s) {
        super.setMessage(s);

        codedMessage = super.getCodedMessage();

        key = super.getKey();

        encode();
    }

    //TO ENCODE
    @Override
    public void setMessage(String s, int k) {

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
    public void setCodedMessage(String s, int k) {
        codedMessage = s;
        key = k;
        decode();
        super.setCodedMessage(codedMessage, key);

    }

    @Override
    public String getCodedMessage() {
        return codedMessage;
    }

    @Override
    public int getKey() {
        return key;
    }

    private void encode() {
        String tempMess = "";
        keyIsPrime = false;
        char targetChar;
        int shiftCount;
        
        //this is what happens below
//            //check if coded message.charat(i) is prime           
//                //if yes, check if key is prime
//                    //if yes, shift to next prime.
//                    //if number > 126, wrap to lower prime.
//                //if no, check if key is even
//                    //if yes, shift up two primes
//                //else shift up 3 primes
//            //if charat(i) is not prime, do nothing        
         
        //check if key is prime
        for (int p = 0; p < 15; p++) {
            if (key == kPrimes[p]) {
                keyIsPrime = true;
            }
        }
        //inspect each char in coded message
        for (int z = 0; z < codedMessage.length(); z++) {
            shiftCount = -1;
            targetChar = codedMessage.charAt(z);
            //compare char to each potential prime
            for (int v = 0; v < 19; ++v) {
                if (targetChar == primes[v]) {
                    if (keyIsPrime == true) {
                        shiftCount = 1;
                    } else {
                        if ((key % 2) == 0) {
                            shiftCount = 2;
                        } // else if ((key % 2) != 0){
                        else {
                            shiftCount = 3;
                        }
                    }
                    shiftCount += v;
                    if (shiftCount > 18) {
                        shiftCount = shiftCount - 19;
                    }
                }
            }
            if (shiftCount > -1) { 
                targetChar = (char) primes[shiftCount];
            } else {
                targetChar = codedMessage.charAt(z);
            }
            tempMess += targetChar;
        }
        codedMessage = tempMess;
    }

    //decode is essentially just encode but subtract your primes instead, refer to commenting above for elaboration on the code
    private void decode() {
        String tempMess = "";
        keyIsPrime = false;
        char targetChar;
        int shiftCount = 0;
        //check if key is prime
        for (int p = 0; p < 15; p++) {
            if (key == kPrimes[p]) {
                keyIsPrime = true;
            }
        }
        //inspect each char in coded message
        for (int z = 0; z < codedMessage.length(); z++) {
            shiftCount = 20;
            targetChar = codedMessage.charAt(z);
            //compare char to each potential prime
            for (int v = 0; v < 19; ++v) {
                if (targetChar == primes[v]) {
                    if (keyIsPrime == true) {
                        shiftCount = -1;
                    } else if ((key % 2) == 0) {
                        shiftCount = -2;
                    } else if ((key % 2) != 0) {
                        shiftCount = -3;
                    }
                    shiftCount += v;
                    if (shiftCount < 0) {
                        shiftCount += 19;
                    }
                }

            }
            if (shiftCount != 20) {
                targetChar = (char) primes[shiftCount];
            } else {
                targetChar = codedMessage.charAt(z);
            }
            tempMess += targetChar;

        }
        codedMessage = tempMess;

    }
}