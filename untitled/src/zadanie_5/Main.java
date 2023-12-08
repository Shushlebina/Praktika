package zadanie_5;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String text = "Все слова в этом тексте встречаются четное количество раз, кроме одного. Определим это слово и узнаем, как часто оно встречается.";

        String oddWord = findOddWord(text);
        System.out.println("Слово, встречающееся нечетное количество раз: " + oddWord);
    }

    public static String findOddWord(String text) {
        Map<String, Integer> wordCounts = new HashMap<>();

// Приводим текст к нижнему регистру и разбиваем на слова
        String[] words = text.toLowerCase().split("\\s+");

// Подсчитываем вхождения каждого слова
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

// Ищем слово, встречающееся нечетное количество раз
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                return entry.getKey();
            }
        }

        return "Такого слова нет";
    }
}
