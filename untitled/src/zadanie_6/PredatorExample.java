package zadanie_6;

import java.util.Scanner;

class Claws {
    public void slash() {
        System.out.println("Когти царапают.");
    }
}

class Teeth {
    public void bite() {
        System.out.println("Зубы кусают.");
    }
}

class Predator {
    Claws claws;
    private Teeth teeth;

    public Predator(Claws claws, Teeth teeth) {
        this.claws = claws;
        this.teeth = teeth;
    }

    public void roar() {
        System.out.println("Хищник рычит.");
    }

    public void run() {
        System.out.println("Хищник бежит.");
    }

    public void sleep() {
        System.out.println("Хищник спит.");
    }

    public void hunt() {
        System.out.println("Хищник добывает пищу:");
        claws.slash();
        teeth.bite();
    }
}

public class PredatorExample {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Создание объектов Когти и Зубы
            Claws claws = new Claws();
            Teeth teeth = new Teeth();

            // Создание объекта Хищник с использованием Когтей и Зубов
            Predator predator = new Predator(claws, teeth);



            // Добавим ввод действий хищника с клавиатуры
            System.out.println("Введите действие хищника (roar, run, sleep, hunt):");
            String action = scanner.next();

            switch (action) {
                case "roar":
                    predator.roar();
                    break;
                case "run":
                    predator.run();
                    break;
                case "sleep":
                    predator.sleep();
                    break;
                case "hunt":
                    predator.hunt();
                    break;
                default:
                    System.out.println("Некорректное действие.");
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
