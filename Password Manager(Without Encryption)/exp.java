public class exp {

    static String encrypt(String org_text, int shift)
    {
        String word = org_text ;
        String new_word = "";

        for (int a = 0 ; a < word.length() ; a++)
        {
            if (Character.isUpperCase(word.charAt(a)))
            {
                 new_word +=  (char)(((word.charAt(a) - 'A' + (shift%26)) % 26) + 'A') ;
            }
            else
            {
                 new_word +=  (char)(((word.charAt(a) - 'a' + (shift%26)) % 26) +  'a');
            }
           
        }
        return new_word ;
    }

    static String decrypt(String text, int shift)
    {
        return encrypt(text,26 - (shift%26)) ;
    }
    public static void main(String[] args)
    {
        String word = "qweQWE123!" ;
        String enc = encrypt(word, 3) ;
        System.out.println(enc);
        String dec = decrypt(enc, 3) ;
        System.out.println(dec);
    }
}
