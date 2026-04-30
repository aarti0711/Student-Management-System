import Service.StudentService;
import Util.PrintUtil;
import model.Student;


import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║    STUDENT MANAGEMENT SYSTEM                         ║
 * ║    Java Collections Framework — Real World Project   ║
 * ╠══════════════════════════════════════════════════════╣
 * ║  Collections used:                                   ║
 * ║   ArrayList      — master student list               ║
 * ║   HashMap        — O(1) lookup by ID                 ║
 * ║   LinkedHashMap  — department grouping               ║
 * ║   TreeMap        — grade-wise sorted grouping        ║
 * ║   HashSet        — unique departments                ║
 * ║   TreeSet        — top rankers (auto-sorted)         ║
 * ║   PriorityQueue  — instant topper access             ║
 * ║   ArrayDeque     — activity log (last 5 events)      ║
 * ║   LinkedList     — waitlist queue (FIFO)             ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class Main {

    public static void main(String[] args) {

        StudentService service = new StudentService();

        // ═══════════════════════════════════════════
        // 1. ADD STUDENTS  →  ArrayList + HashMap
        // ═══════════════════════════════════════════
        PrintUtil.header("1. Adding Students  [ArrayList + HashMap]");

        service.addStudent(new Student(101, "Aarav Shah",      "Computer Science", 92.5));
        service.addStudent(new Student(102, "Priya Mehta",     "Electronics",      87.0));
        service.addStudent(new Student(103, "Ravi Patel",      "Computer Science", 74.5));
        service.addStudent(new Student(104, "Sneha Desai",     "Mechanical",       68.0));
        service.addStudent(new Student(105, "Karan Joshi",     "Electronics",      91.0));
        service.addStudent(new Student(106, "Ananya Gupta",    "Computer Science", 83.5));
        service.addStudent(new Student(107, "Vikram Singh",    "Mechanical",       55.0));
        service.addStudent(new Student(108, "Divya Sharma",    "Electronics",      78.0));
        service.addStudent(new Student(109, "Rohan Verma",     "Computer Science", 95.0));
        service.addStudent(new Student(110, "Meera Nair",      "Mechanical",       61.5));

        // Duplicate ID test
        service.addStudent(new Student(101, "Duplicate Test",  "Computer Science", 50.0));

        System.out.println("\n  Total students: " + service.totalStudents());

        // ═══════════════════════════════════════════
        // 2. FIND BY ID  →  HashMap O(1)
        // ═══════════════════════════════════════════
        PrintUtil.header("2. Find by ID  [HashMap — O(1)]");

        Student found = service.findById(105);
        System.out.println("  Found ID 105: " + found);

        Student notFound = service.findById(999);
        System.out.println("  Found ID 999: " + notFound);  // null

        // ═══════════════════════════════════════════
        // 3. SEARCH BY NAME  →  ArrayList iteration
        // ═══════════════════════════════════════════
        PrintUtil.header("3. Search by Name  [ArrayList iteration]");

        PrintUtil.section("Search 'ra'");
        PrintUtil.printList(service.searchByName("ra"));

        PrintUtil.section("Search 'shah'");
        PrintUtil.printList(service.searchByName("shah"));

        // ═══════════════════════════════════════════
        // 4. SORT  →  Collections.sort + Comparator
        // ═══════════════════════════════════════════
        PrintUtil.header("4. Sorting  [Collections.sort + Comparator]");

        PrintUtil.section("Sorted by Name (A→Z)");
        PrintUtil.printList(service.sortByName());

        PrintUtil.section("Sorted by Marks (High→Low)  [Comparable]");
        PrintUtil.printList(service.sortByMarks());

        // ═══════════════════════════════════════════
        // 5. TOPPER  →  PriorityQueue O(1)
        // ═══════════════════════════════════════════
        PrintUtil.header("5. Topper  [PriorityQueue — O(1) peek]");

        Student topper = service.getTopper();
        System.out.println("  Current Topper: " + topper);

        // ═══════════════════════════════════════════
        // 6. TOP N  →  TreeSet (auto-sorted)
        // ═══════════════════════════════════════════
        PrintUtil.header("6. Top 3 Students  [TreeSet — auto-sorted by marks]");

        PrintUtil.printRanked(service.getTopN(3));

        // ═══════════════════════════════════════════
        // 7. BY DEPARTMENT  →  LinkedHashMap
        // ═══════════════════════════════════════════
        PrintUtil.header("7. Students by Department  [LinkedHashMap]");

        PrintUtil.printDepartmentMap(service.getByDepartment());

        // ═══════════════════════════════════════════
        // 8. GRADE-WISE  →  TreeMap (sorted grades)
        // ═══════════════════════════════════════════
        PrintUtil.header("8. Grade-wise Grouping  [TreeMap — sorted keys]");

        PrintUtil.printGradeMap(service.groupByGrade());

        // ═══════════════════════════════════════════
        // 9. DEPT AVERAGES  →  LinkedHashMap + stream
        // ═══════════════════════════════════════════
        PrintUtil.header("9. Department Averages  [LinkedHashMap]");

        PrintUtil.printAverages(service.departmentAverages());

        // ═══════════════════════════════════════════
        // 10. UNIQUE DEPARTMENTS  →  HashSet
        // ═══════════════════════════════════════════
        PrintUtil.header("10. Unique Departments  [HashSet]");

        System.out.println("  Departments: " + service.getDepartments());

        // ═══════════════════════════════════════════
        // 11. UPDATE MARKS  →  TreeSet + PriorityQueue
        // ═══════════════════════════════════════════
        PrintUtil.header("11. Update Marks  [TreeSet + PriorityQueue re-insertion]");

        System.out.println("  Before update — Topper: " + service.getTopper());
        service.updateMarks(102, 98.0);  // Priya gets 98 → becomes topper
        System.out.println("  After update  — Topper: " + service.getTopper());

        PrintUtil.section("New Top 3");
        PrintUtil.printRanked(service.getTopN(3));

        // ═══════════════════════════════════════════
        // 12. REMOVE STUDENT
        // ═══════════════════════════════════════════
        PrintUtil.header("12. Remove Student");

        service.removeStudent(107);   // Vikram Singh
        service.removeStudent(999);   // not found
        System.out.println("  Total after removal: " + service.totalStudents());

        // ═══════════════════════════════════════════
        // 13. WAITLIST  →  LinkedList Queue (FIFO)
        // ═══════════════════════════════════════════
        PrintUtil.header("13. Waitlist  [LinkedList — FIFO Queue]");

        Student w1 = new Student(201, "Arjun Kapoor",  "Computer Science", 88.0);
        Student w2 = new Student(202, "Nisha Reddy",   "Electronics",      76.5);

        service.addToWaitlist(w1);
        service.addToWaitlist(w2);
        System.out.println("  Waitlist size: " + service.waitlistSize());

        service.admitFromWaitlist();  // Arjun admitted first (FIFO)
        service.admitFromWaitlist();  // Nisha admitted second
        service.admitFromWaitlist();  // empty

        // ═══════════════════════════════════════════
        // 14. ACTIVITY LOG  →  ArrayDeque (last 5)
        // ═══════════════════════════════════════════
        PrintUtil.header("14. Activity Log  [ArrayDeque — sliding window of 5]");

        PrintUtil.printLog(service.getActivityLog());

        // ═══════════════════════════════════════════
        // 15. FINAL SUMMARY
        // ═══════════════════════════════════════════
        PrintUtil.header("15. Final Summary");

        System.out.println("  Total students   : " + service.totalStudents());
        System.out.println("  Departments      : " + service.getDepartments());
        System.out.println("  Overall Topper   : " + service.getTopper());
        System.out.println("  Waitlist         : " + service.waitlistSize() + " pending");
        PrintUtil.section("Final Rankings (Top 5)");
        PrintUtil.printRanked(service.getTopN(5));

        System.out.println("\n" + "═".repeat(72));
        System.out.println("  Collections used: ArrayList, HashMap, LinkedHashMap, TreeMap,");
        System.out.println("  HashSet, TreeSet, PriorityQueue, ArrayDeque, LinkedList (Queue)");
        System.out.println("═".repeat(72) + "\n");
    }
}