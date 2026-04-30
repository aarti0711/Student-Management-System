package Util;

import model.Student;

import java.util.List;
import java.util.Map;

public class PrintUtil {

    private static final String LINE = "─".repeat(72);
    private static final String THIN = "·".repeat(72);

    public static void header(String title) {
        System.out.println("\n" + LINE);
        System.out.println("  " + title.toUpperCase());
        System.out.println(LINE);
    }

    public static void section(String title) {
        System.out.println("\n  ── " + title + " ──");
    }

    public static void printList(List<Student> list) {
        if (list.isEmpty()) { System.out.println("  (no results)"); return; }
        list.forEach(s -> System.out.println("    " + s));
    }

    public static void printRanked(List<Student> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("    #%-2d %s%n", i + 1, list.get(i));
        }
    }

    public static void printDepartmentMap(Map<String, List<Student>> map) {
        map.forEach((dept, students) -> {
            System.out.println("    [" + dept + "] — " + students.size() + " student(s)");
            students.forEach(s -> System.out.println("         " + s));
        });
    }

    public static void printGradeMap(Map<String, List<Student>> map) {
        map.forEach((grade, students) -> {
            System.out.println("    Grade " + grade + " — " + students.size() + " student(s)");
            students.forEach(s -> System.out.println("         " + s));
        });
    }

    public static void printAverages(Map<String, Double> avg) {
        avg.forEach((dept, a) ->
                System.out.printf("    %-15s → Avg: %.1f%n", dept, a));
    }

    public static void printLog(Iterable<String> log) {
        log.forEach(entry -> System.out.println("    • " + entry));
    }

    public static void divider() {
        System.out.println("  " + THIN);
    }
}
