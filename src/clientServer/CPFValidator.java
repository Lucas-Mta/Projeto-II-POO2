package clientServer;

import java.util.HashSet;
import java.util.Set;

public class CPFValidator {
    private static final Set<String> usedCpfs = new HashSet<>();

    public static boolean isValid(String cpf) {
        String cpfAux = cpf.replace(".", "").replace("-", "");
        int[] cpfNum = cpfAux.chars().map(c -> c - '0').toArray();
        if (cpfAux.length() != 11 || !cpfAux.matches("\\d+") || cpfAux.chars().allMatch(c -> c == cpfAux.charAt(0))) {
            return false;
        }

        if (!isValidCPF(cpfNum)) {
            return false;
        }

        return isUnique(cpfAux);
    }

    public static boolean isValidCPF(int[] cpfNum) {
        // Calcular o primeiro dígito verificador
        int firstDigit = calculateFirstDigit(cpfNum);
        // Calcular o segundo dígito verificador
        int secondDigit = calculateSecondDigit(cpfNum, firstDigit);

        // Comparar os dígitos verificadores calculados com os dígitos do CPF

        return ((cpfNum[9] == firstDigit) && (cpfNum[10] == secondDigit));
    }

    private static int calculateFirstDigit(int[] cpfNum) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfNum[i] * (10 - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    private static int calculateSecondDigit(int[] cpfNum, int firstDigit) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfNum[i] * (11 - i);
        }
        sum += firstDigit * 2;
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    private static boolean isUnique(String cpfAux) {
        if (usedCpfs.contains(cpfAux)) {
            return false;
        }
        usedCpfs.add(cpfAux);
        return true;
    }
}
