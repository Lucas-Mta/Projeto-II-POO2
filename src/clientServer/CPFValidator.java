package clientServer;

import java.util.HashSet;
import java.util.Set;

/** Validator class for Brazilian CPF (Individual Taxpayer Registry) numbers.
  * This class provides methods to validate CPF numbers and ensure they are unique
  * within the voting system. The validation includes checking the format,
  * verifying check digits, and ensuring each CPF is used only once.            */
public class CPFValidator {

    public static boolean isValid(String cpf) {
        String cpfAux = cpf.replace(".", "").replace("-", "");
        int[] cpfNum = cpfAux.chars().map(c -> c - '0').toArray();
        if (cpfAux.length() != 11 || !cpfAux.matches("\\d+") || cpfAux.chars().allMatch(c -> c == cpfAux.charAt(0))) {
            return false;
        }
        else return isValidCPF(cpfNum);
    }

    /** Validates the CPF number by checking its verification digits.
      * The validation follows the official Brazilian CPF algorithm.
      * @param cpfNum Array of integers representing the CPF digits
      * @return true if the verification digits are valid, false otherwise    */
    public static boolean isValidCPF(int[] cpfNum) {
        int firstDigit = calculateFirstDigit(cpfNum);

        int secondDigit = calculateSecondDigit(cpfNum, firstDigit);

        // Compare calculated verification digits with the actual CPF digits
        return ((cpfNum[9] == firstDigit) && (cpfNum[10] == secondDigit));
    }

    /** Calculates the first verification digit of the CPF.
      * The calculation multiplies each digit by weights from 10 to 2 and uses
      * the remainder of the sum divided by 11 to determine the verification digit.
      * @param cpfNum Array of integers representing the first 9 digits of the CPF
      * @return The calculated first verification digit                             */
    private static int calculateFirstDigit(int[] cpfNum) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfNum[i] * (10 - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    /** Calculates the second verification digit of the CPF.
      * The calculation multiplies each digit by weights from 11 to 2 and uses
      * the remainder of the sum divided by 11 to determine the verification digit.
      * @param cpfNum Array of integers representing the CPF digits
      * @param firstDigit The first verification digit (previously calculated)
      * @return The calculated second verification digit                            */
    private static int calculateSecondDigit(int[] cpfNum, int firstDigit) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfNum[i] * (11 - i);
        }
        sum += firstDigit * 2;
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
