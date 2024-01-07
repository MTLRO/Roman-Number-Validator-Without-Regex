import java.util.ArrayList;
public class RomanNumbers {


public static boolean romanNumberValidator(String number) {

        //Test empty string
        if (number.isEmpty()) {
            System.out.println("Empty String.");
            return false;
        }

        //Initialize letter counts. Last slot is used for invalid Letter encounter.
        int[] letterCounts = {1, 1, 1, 1, 1, 1, 1, -1};

        //Initialize banned letters
        ArrayList<Character> bannedLetters = new ArrayList<>();

        for (int i = 0; i < number.length(); i++) {
            //Get current character
            char curr = number.charAt(i);
            //Get next character.
            char next = '.'; //If last character, next = '.'
            if (i < number.length() - 1) {
                next = number.charAt(i + 1);
            }
            char prev = '.'; //If last character, next = '.'
            if (i > 0) {
                prev = number.charAt(i - 1);
            }
            int index = indexPicker(curr); //Index used to update letterCount.
            //Test roman rules
            int result = validatorHelper(curr, bannedLetters, letterCounts[index], next, prev);

            if (result == -1) {
                //System.out.println("Invalid Roman Numeral.");
                return false;
            } else {
                letterCounts[result]++; //Increment the letterCount.
            }
        }
        // System.out.println("Valid Roman Numeral.");
        return true;
    }

    public static int validatorHelper(char currentLetter, ArrayList<Character> bannedLetters, int sCount, char next, char prev) {

        if (sCount == -1) {

            return -1;
        }
        //Check if a sequence this letter has already been used before
        if (bannedLetters.contains(currentLetter)) {
            return -1;
        }

        //test special cases IV,IX,XL,XC,CD,CM.

        //only possible number before 40:
        // 800, 700, 600, 300, 200, 100 --> C
        // 500 or 400 -> D
        // 1000 or 900 -> M
        // Exception: the number XL.
        if (currentLetter == 'X' && next == 'L') {
            if (prev != 'C' && prev != 'M' && prev != 'D' && prev != '.') {
                return -1;
            }
        }
        //only possible number before 90:
        // 800, 700, 600, 300, 200, 100 --> C
        // 500 or 400 -> D
        // 1000 or 900 -> M
        // Exception: the number XC.
        if (currentLetter == 'X' && next == 'C') {
            if (prev != 'C' && prev != 'M' && prev != 'D' && prev != '.') {
                return -1;
            }
        }
        //only possible number before 400:
        // 1000 -> M
        // Exception: the number CD.
        if (currentLetter == 'C' && next == 'D') {
            if (prev != 'M' && prev != '.') {
                return -1;
            }
        }
        //only possible number before 900:
        // 1000 -> M
        // Exception: the number CM.
        if (currentLetter == 'C' && next == 'M') {
            if (prev != 'M' && prev != '.') {
                return -1;
            }
        }

        //Forbidden Combinations: IVI, IXI, XLX, XCX, CDC, CMC
        //IVI and IXI are impossible because of previous constrains.
        if (prev == 'X' && currentLetter == 'L' && next == prev) {
            return -1;
        }
        if (prev == 'X' && currentLetter == 'C' && next == prev) {
            return -1;
        }
        if (prev == 'C' && currentLetter == 'D' && next == prev) {
            return -1;
        }
        if (prev == 'C' && currentLetter == 'M' && next == prev) {
            return -1;
        }
        if (prev == 'I' && currentLetter == 'I') {
            if (next != 'I' && next != '.') {
                return -1;
            }
        }
        //If three I's, must be end of word.
        if (currentLetter == 'I' && sCount == 3) {
            bannedLetters.add('I');
            bannedLetters.add('V');
            bannedLetters.add('X');
            bannedLetters.add('L');
            bannedLetters.add('C');
            bannedLetters.add('D');
            bannedLetters.add('M');
        }
        //IV Can only occur at the end of the string. Any number can be before IV.
        if (currentLetter == 'V' && next != '.' && prev == 'I') {
            return -1;
        }
        //IX Can only occur at the end of the string. Any number can be before IX.
        if (currentLetter == 'X' && next != '.' && prev == 'I') {
            return -1;
        }
        //Cannot have more than one M in the middle of a string.
        if (currentLetter == 'M' && next == 'M' && prev != '.') {
            return -1;
        }

        //If letters appear more than four times, incorrect. Exception M as it is the last roman numeral.
        if (prev == currentLetter && sCount > 3 && currentLetter != 'M') {
            return -1;
        }
        if (next != '.') {
            if (currentLetter == 'I') {
                bannedLetters.add('L');
                bannedLetters.add('C');
                bannedLetters.add('D');
                bannedLetters.add('M');
            }
            if (currentLetter == 'X') {
                bannedLetters.add('D');
                bannedLetters.add('M');
            }
            if (currentLetter == 'V') {
                bannedLetters.add('V');
                bannedLetters.add('X');
                bannedLetters.add('L');
                bannedLetters.add('C');
                bannedLetters.add('D');
                bannedLetters.add('M');
            }
            //If you have a 90, you cannot add a bigger number.
            if (currentLetter == 'C' && prev == 'X') {
                bannedLetters.add('C');
                bannedLetters.add('D');
                bannedLetters.add('M');
                bannedLetters.add('L');
            }
            if (currentLetter == 'L') {
                bannedLetters.add('L');
                bannedLetters.add('C');
                bannedLetters.add('D');
                bannedLetters.add('M');
            }
            if (currentLetter == 'D') {
                bannedLetters.add('D');
                bannedLetters.add('M');
            }
        }
        //find appropriate index for letter count incrementation
        if (currentLetter == 'I') {
            return 0;
        }
        if (currentLetter == 'V') {
            return 1;
        }
        if (currentLetter == 'X') {
            return 2;
        }
        if (currentLetter == 'L') {
            return 3;
        }
        if (currentLetter == 'C') {
            return 4;
        }
        if (currentLetter == 'D') {
            return 5;
        }
        if (currentLetter == 'M') {
            return 6;
        }
        return -1;
    } //helper

    public static int indexPicker(char currentLetter) {
        int index = 7;
        if (currentLetter == 'I') {
            index = 0;
        }
        if (currentLetter == 'V') {
            index = 1;
        }
        if (currentLetter == 'X') {
            index = 2;
        }
        if (currentLetter == 'L') {
            index = 3;
        }
        if (currentLetter == 'C') {
            index = 4;
        }
        if (currentLetter == 'D') {
            index = 5;
        }
        if (currentLetter == 'M') {
            index = 6;
        }
        return index;
    } //helper
}
