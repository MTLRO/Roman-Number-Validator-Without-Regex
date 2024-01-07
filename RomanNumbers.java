import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RomanNumbers {


    //Utilities
    public static String convertToRoman(int number) {

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        StringBuilder result = new StringBuilder();

        for (int i = romanSymbols.length - 1; i >= 0; i--) {
            while (number >= getRomanValue(romanSymbols[i])) {
                result.append(romanSymbols[i]);
                number -= getRomanValue(romanSymbols[i]);
            }
        }

        return result.toString();
    } //convert integer to roman

     public static int romanToInt(String s) {
        int sum = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);

            char next = '.';
            if (i + 1 < s.length()) {
                next = s.charAt(i + 1);
            }
            if (c == 'M') {
                sum = sum + 1000;
            } else if (c == 'D') {
                sum = sum + 500;
            } else if (c == 'C') {
                if (next == 'D') {
                    sum = sum + 400;
                    i++;
                } else if (next == 'M') {
                    sum = sum + 900;
                    i++;
                } else {
                    sum = sum + 100;
                }
            } else if (c == 'L') {
                sum = sum + 50;
            }

            if (c == 'X') {
                if (next == 'L') {
                    sum = sum + 40;
                    i++;
                } else if (next == 'C') {
                    sum = sum + 90;
                    i++;
                } else {
                    sum = sum + 10;
                }
            } else if (c == 'V') {
                sum = sum + 5;
            } else if (c == 'I') {
                if (next == 'X') {

                    sum = sum + 9;
                    i++;
                } else if (next == 'V') {
                    sum = sum + 4;
                    i++;
                } else {
                    sum = sum + 1;
                }
            }
            i++;
        }
        return sum;
    }

    public static int getRomanValue(String symbol) {
        switch (symbol) {
            case "I":
                return 1;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "IX":
                return 9;
            case "X":
                return 10;
            case "XL":
                return 40;
            case "L":
                return 50;
            case "XC":
                return 90;
            case "C":
                return 100;
            case "CD":
                return 400;
            case "D":
                return 500;
            case "CM":
                return 900;
            case "M":
                return 1000;
            default:
                return 0;
        }
    } // //converts a single roman "digit" to an integer.

    public static String generateRandomRomanString(int length, String startingNumerals, String excludedNumerals) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        // Array of valid Roman numerals
        char[] romanNumerals = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

        // Ensure all starting and excluded numerals are uppercase
        startingNumerals = startingNumerals.toUpperCase();
        excludedNumerals = excludedNumerals.toUpperCase();

        // Create a set to store excluded numerals for efficient lookup
        HashSet<Character> excludedSet = new HashSet<>();
        for (char excludedNumeral : excludedNumerals.toCharArray()) {
            excludedSet.add(excludedNumeral);
        }

        // Append the starting numerals to the StringBuilder
        stringBuilder.append(startingNumerals);

        // Generate the remaining Roman numerals randomly, excluding specified numerals
        for (int i = startingNumerals.length(); i < length; i++) {
            char randomNumeral;
            do {
                int randomIndex = random.nextInt(romanNumerals.length);
                randomNumeral = romanNumerals[randomIndex];
            } while (excludedSet.contains(randomNumeral));

            stringBuilder.append(randomNumeral);
        }

        return stringBuilder.toString();
    } //Generates random strings of roman numerals of specific length.

    //Testers:

    public static void gibberishTester(int length) {
        System.out.println("Out of 400 gibberish roman numbers, here are the one who pose a contradiction: ");
        int count = 0;
        for (int i = 0; i < 400; i++) {
            String string = generateRandomRomanString(length, "", "M");
            int integer = romanToInt(string);
            String stringToCompare = convertToRoman(integer);
            if (romanNumberValidator(string) ^ string.equals(stringToCompare)) {
                System.out.print("Gibberish string: " + string + " ---> ");
                System.out.print("Value: " + integer + " ---> ");
                System.out.print("Valid way: " + stringToCompare + " ---> ");
                if (!string.equals(stringToCompare)) {
                    System.out.print("Strings not equal ---> ");
                } else {
                    System.out.print("Good ---> ");
                }
                System.out.print("Your function: " + romanNumberValidator(string));
                System.out.println(" ");
                System.out.println("problem");
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No contradictions.");
        }
    } //Tests 400 random "roman" strings, shows the ones that yield a contradiction between the output of romanNumberValidator and the actual answer.

    public static void specificTester(String input) {
        System.out.print(input + ": ");
        System.out.print(romanNumberValidator(input));
        System.out.println(" ");
    } //Tests a number that you specify.

    public static void validRomanTester(int amount) {
        int count = 0;
        for (int i = 1; i <= amount; i++) {
            if (!romanNumberValidator(convertToRoman(i))) {
                System.out.print(convertToRoman(i) + ": ");
                System.out.print(romanNumberValidator(convertToRoman(i)));
                System.out.println(" ");
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No false negatives until " + amount + ".");
        }
    } //Generates a specific amount of valid roman numbers and tests them. If the output of romanNumberValidator is false, it will output.

    //romanNumberValidator
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

//Next

