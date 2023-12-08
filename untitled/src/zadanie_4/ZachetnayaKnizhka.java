package zadanie_4;

import java.util.ArrayList;
import java.util.List;

class ZaOtchet {
    private String subject;
    private String result;

    public ZaOtchet(String subject, String result) {
        this.subject = subject;
        this.result = result;
    }

    public String getSubject() {
        return subject;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Предмет: " + subject + ", Результат: " + result;
    }
}

class Zaochety {
    private List<ZaOtchet> zaOtchets;

    public Zaochety() {
        this.zaOtchets = new ArrayList<>();
    }

    public void addZaOtchet(String subject, String result) {
        ZaOtchet zaOtchet = new ZaOtchet(subject, result);
        zaOtchets.add(zaOtchet);
    }

    public List<ZaOtchet> getZaOtchets() {
        return zaOtchets;
    }
}

public class ZachetnayaKnizhka {
    private String studentName;
    private Zaochety zaochety;

    public ZachetnayaKnizhka(String studentName) {
        this.studentName = studentName;
        this.zaochety = new Zaochety();
    }

    public void addSessionInfo(String subject, String result) {
        zaochety.addZaOtchet(subject, result);
    }

    public void printZachetnayaKnizhka() {
        System.out.println("Зачетная книжка для студента: " + studentName);
        List<ZaOtchet> zaOtchets = zaochety.getZaOtchets();
        for (ZaOtchet zaOtchet : zaOtchets) {
            System.out.println(zaOtchet);
        }
    }

    public static void main(String[] args) {
        ZachetnayaKnizhka studentBook = new ZachetnayaKnizhka("Иван Иванов");
        studentBook.addSessionInfo("Математика", "Зачет");
        studentBook.addSessionInfo("Физика", "Экзамен - отлично");
        studentBook.addSessionInfo("История", "Зачет");

        studentBook.printZachetnayaKnizhka();
    }
}
