public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                if(i*numCols+j+1>str.length()){
                    letterBlock[i][j] = "A";
                }
                else{
                    letterBlock[i][j] = str.substring(i*numCols+j,i*numCols+j+1);
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String encrypted = "";
        for(int i=0;i<numCols;i++){
            for(int j=0;j<numRows;j++){
                encrypted+=letterBlock[j][i];
            }
        }
        return encrypted;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String encrypted = "";
        while(message.length()>numRows*numCols){
            fillBlock(message.substring(0,numCols*numRows));
            encrypted += encryptBlock();
            message = message.substring(numCols*numRows);
        }
        fillBlock(message);
        encrypted += encryptBlock();
        return encrypted;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String decrypted = "";
        String temp=encryptedMessage;
        int splits=encryptedMessage.length()/(numCols*numRows);
        while (splits>0) {
            decryptBlock(temp);
            encryptBlock();
            for (String[] strings : letterBlock) {
                for (int s = 0; s < letterBlock[0].length; s++) {
                    if(strings[s].equals("A")&&(splits-1==0)){
                        decrypted=decrypted;
                    }else{
                        decrypted += strings[s];
                    }}
            }
            temp=temp.substring(numCols*numRows);
            splits--;
        }
        return decrypted;
    }

    public void decryptBlock(String encrypted) {
        int count=0;
        for (int col = 0; col < letterBlock[0].length; col++) {
            for (int row = 0; row < letterBlock.length; row++) {
                if (row < encrypted.length()) {
                    letterBlock[row][col] = encrypted.substring(count, count + 1);
                    count++;
                } else {
                    letterBlock[row][col] = "A";
                }
            }
        }
    }
}