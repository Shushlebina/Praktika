package zadanie_3;

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
    private Claws claws;
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
        // Создание объектов Когти и Зубы
        Claws claws = new Claws();
        Teeth teeth = new Teeth();

        // Создание объекта Хищник с использованием Когтей и Зубов
        Predator predator = new Predator(claws, teeth);

        // Вызов методов объекта Хищник с выводом в консоль
        predator.roar();
        predator.run();
        predator.sleep();
        predator.hunt();

        // Добавим ввод действий хищника с клавиатуры
        Scanner scanner = new Scanner(System.in);
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
    }
}

