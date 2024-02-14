import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            String[] parts = input.split(" ");
            if (parts.length != 3)
                throw new IllegalArgumentException("Неправильный формат ввода");

            String Left = parts[0];
            String Midle = parts[1];
            String Right = parts[2];

            boolean Roman =  Roman(Left) && Roman(Right);
            int left = convertToInt(Left, Roman);
            int right = convertToInt(Right, Roman);

            if (left < 1 || left > 10 || right < 1 || right > 10)
                throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10");

            int result;
            switch (Midle) {
                case "+":
                    result = left + right;
                    break;
                case "-":
                    result = left - right;
                    break;
                case "*":
                    result = left * right;
                    break;
                case "/":
                    if (right == 0)
                        throw new ArithmeticException("Деление на ноль");
                    result = left / right;
                    break;
                default:
                    throw new IllegalAccessException("Неподдерживаемая операция: " + Midle);
            }

            if (Roman) {
                if (result == 0)
                    throw new IllegalAccessException("Результат работы с римскими числами не может быть меньше или равен 0");
                System.out.println(toRoman(result));
            }else {
                System.out.println(result);
            }

        }catch (Exception e) {
            System.err.println("Ошибка "+ e.getMessage());
        }

    }

    private static boolean Roman(String input) {
        return input.matches("[IVX]+");
    }
    private static int convertToInt(String input, boolean Roman) {
        if (Roman) {
            int num = 0;
            int preValue = 0;
            for (int i = input.length() - 1; i>=0; i--) {
                int value = romanMap.get(input.charAt(i));
                if (value<preValue)
                    num -=value;
                else
                    num +=value;
                preValue = value;
            }
            return num;
        } else {
            return Integer.parseInt(input);
        }
    }

    private static final Map<Character, Integer> romanMap = new HashMap<>();
    static {
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
    }

    private static final String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private static String toRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (num > 0) {
            int count = num / romanMap.get(romanSymbols[i]);
            num %= romanMap.get(romanSymbols[i]);
            for (int j = 0; j < count; j++) {
                sb.append(romanSymbols[i]);
            }
            i++;
        }
        return sb.toString();
    }
}
