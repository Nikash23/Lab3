import java.util.*;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
    }

    // 1. Энтропия для {A,B,C}
    public static void task1() {
        double pA = 0.5, pB = 0.3, pC = 0.2;
        double H = 0;

        if (pA > 0) H += pA * (Math.log(pA) / Math.log(2));
        if (pB > 0) H += pB * (Math.log(pB) / Math.log(2));
        if (pC > 0) H += pC * (Math.log(pC) / Math.log(2));
        H = -H;

        System.out.println("1. Энтропия для {A,B,C}: " + H + " бит");
    }

    // 2. Максимальная энтропия для 4 исходов
    public static void task2() {
        double p = 0.25;
        double H = 0;

        if (p > 0) H += p * (Math.log(p) / Math.log(2));
        H = -H * 4;

        System.out.println("2. Максимальная энтропия для 4 исходов: " + H + " бит");
    }

    // 3. Средняя энтропия источника
    public static void task3() {
        double pa = 0.1, pb = 0.2, pc = 0.3, pd = 0.2, pe = 0.2;
        double H = 0;

        if (pa > 0) H += pa * (Math.log(pa) / Math.log(2));
        if (pb > 0) H += pb * (Math.log(pb) / Math.log(2));
        if (pc > 0) H += pc * (Math.log(pc) / Math.log(2));
        if (pd > 0) H += pd * (Math.log(pd) / Math.log(2));
        if (pe > 0) H += pe * (Math.log(pe) / Math.log(2));
        H = -H;

        System.out.println("3. Средняя энтропия источника: " + H + " бит");
    }

    // 4. n для энтропии 3 бита
    public static void task4() {
        int n = (int) Math.pow(2, 3);
        System.out.println("4. n для энтропии 3 бита: " + n);
    }

    // 5. Объяснение уменьшения энтропии
    public static void task5() {
        System.out.println("5. Энтропия уменьшается, потому что распределение становится менее равномерным, " +
                "увеличивается предсказуемость системы.");
    }

    // 6. Код Хаффмана
    public static void task6() {
        Map<Character, Double> probabilities = new HashMap<>();
        probabilities.put('A', 0.4);
        probabilities.put('B', 0.2);
        probabilities.put('C', 0.2);
        probabilities.put('D', 0.1);
        probabilities.put('E', 0.1);

        HuffmanCode huffman = new HuffmanCode(probabilities);
        System.out.println("6. Коды Хаффмана: " + huffman.getCodeMap());
        System.out.println("   Средняя длина кода: " + huffman.calculateAverageLength());
    }

    // 7. Оптимальность кода Хаффмана
    public static void task7() {
        System.out.println("7. Код Хаффмана оптимален, так как обеспечивает минимальную среднюю длину кода " +
                "для заданного распределения вероятностей, и является префиксным.");
    }

    // 8. Арифметический код для "ABB"
    public static void task8() {
        Map<Character, Double> prob8 = new HashMap<>();
        prob8.put('A', 0.6);
        prob8.put('B', 0.4);
        System.out.println("8. Арифметический код для 'ABB': " + arithmeticEncode("ABB", prob8));
    }

    // 9. Декодирование сообщения
    public static void task9() {
        Map<Character, String> codeMap = new HashMap<>();
        codeMap.put('A', "0");
        codeMap.put('B', "10");
        codeMap.put('C', "11");
        String encoded = "01010011";
        System.out.println("9. Декодированное сообщение: " + decode(encoded, codeMap));
    }

    // 10. Избыточность кода
    public static void task10() {
        double avgLength = 2.5;
        double entropy = 2.1;
        System.out.println("10. Избыточность кода: " + (avgLength - entropy) + " бит");
    }

    // Реализация кода Хаффмана
    static class HuffmanCode {
        private final Map<Character, String> codeMap = new HashMap<>();
        private final Map<Character, Double> probabilities;

        public HuffmanCode(Map<Character, Double> probabilities) {
            this.probabilities = probabilities;
            buildTree();
        }

        private void buildTree() {
            PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

            for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
                queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
            }

            while (queue.size() > 1) {
                HuffmanNode left = queue.poll();
                HuffmanNode right = queue.poll();
                HuffmanNode parent = new HuffmanNode(left.probability + right.probability, left, right);
                queue.add(parent);
            }

            buildCodeMap(queue.poll(), "");
        }

        private void buildCodeMap(HuffmanNode node, String code) {
            if (node.isLeaf()) {
                codeMap.put(node.character, code);
            } else {
                buildCodeMap(node.left, code + "0");
                buildCodeMap(node.right, code + "1");
            }
        }

        public Map<Character, String> getCodeMap() {
            return codeMap;
        }

        public double calculateAverageLength() {
            double avgLength = 0;
            for (Map.Entry<Character, String> entry : codeMap.entrySet()) {
                avgLength += entry.getValue().length() * probabilities.get(entry.getKey());
            }
            return avgLength;
        }

        private static class HuffmanNode implements Comparable<HuffmanNode> {
            char character;
            double probability;
            HuffmanNode left, right;

            HuffmanNode(char character, double probability) {
                this.character = character;
                this.probability = probability;
            }

            HuffmanNode(double probability, HuffmanNode left, HuffmanNode right) {
                this.probability = probability;
                this.left = left;
                this.right = right;
                this.character = '\0';
            }

            boolean isLeaf() {
                return left == null && right == null;
            }

            @Override
            public int compareTo(HuffmanNode other) {
                return Double.compare(this.probability, other.probability);
            }
        }
    }

    // Арифметическое кодирование
    public static double arithmeticEncode(String input, Map<Character, Double> probabilities) {
        double low = 0.0;
        double high = 1.0;
        double range = 1.0;

        for (char c : input.toCharArray()) {
            double charLow = 0.0;
            double charHigh = 0.0;

            for (Map.Entry<Character, Double> entry : probabilities.entrySet()) {
                if (entry.getKey() == c) {
                    charHigh = charLow + entry.getValue();
                    break;
                }
                charLow += entry.getValue();
            }

            double newLow = low + range * charLow;
            double newHigh = low + range * charHigh;
            low = newLow;
            high = newHigh;
            range = high - low;
        }

        return (low + high) / 2;
    }

    // Декодирование
    public static String decode(String encoded, Map<Character, String> codeMap) {
        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < encoded.length(); i++) {
            temp.append(encoded.charAt(i));
            for (Map.Entry<Character, String> entry : codeMap.entrySet()) {
                if (entry.getValue().equals(temp.toString())) {
                    result.append(entry.getKey());
                    temp = new StringBuilder();
                    break;
                }
            }
        }

        return result.toString();
    }
}