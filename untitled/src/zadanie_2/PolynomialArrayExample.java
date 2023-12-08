package zadanie_2;

import java.util.Arrays;

class RationalFraction {
    private int numerator;
    private int denominator;

    public RationalFraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    // Метод сложения рациональных дробей
    public RationalFraction add(RationalFraction other) {
        int resultNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int resultDenominator = this.denominator * other.denominator;
        return new RationalFraction(resultNumerator, resultDenominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}

class Polynomial {
    private RationalFraction[] coefficients;

    public Polynomial(RationalFraction[] coefficients) {
        this.coefficients = coefficients;
    }

    // Метод сложения полиномов
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        RationalFraction[] resultCoefficients = new RationalFraction[maxLength];

        for (int i = 0; i < maxLength; i++) {
            RationalFraction term1 = (i < this.coefficients.length) ? this.coefficients[i] : new RationalFraction(0, 1);
            RationalFraction term2 = (i < other.coefficients.length) ? other.coefficients[i] : new RationalFraction(0, 1);

            resultCoefficients[i] = term1.add(term2);
        }

        return new Polynomial(resultCoefficients);
    }

    public RationalFraction[] getCoefficients() {
        return coefficients;
    }
}

public class PolynomialArrayExample {
    public static void main(String[] args) {
        int n = 3; // Количество полиномов в массиве

// Создание массива полиномов
        Polynomial[] polynomials = new Polynomial[n];
        for (int i = 0; i < n; i++) {
// Инициализация каждого полинома (предположим, что у нас есть массив коэффициентов)
            RationalFraction[] coefficients = {
                    new RationalFraction(1, 1),
                    new RationalFraction(2, 1),
                    new RationalFraction(3, 1)
// Дополните массив в соответствии с вашими требованиями
            };
            polynomials[i] = new Polynomial(coefficients);
        }

// Вычисление суммы полиномов в массиве
        Polynomial sum = Arrays.stream(polynomials)
                .reduce(new Polynomial(new RationalFraction[0]), Polynomial::add);

// Вывод суммы полиномов
        System.out.println("Сумма полиномов:");
        for (RationalFraction coefficient : sum.getCoefficients()) {
            System.out.print(coefficient + "\t");
        }
    }
}

