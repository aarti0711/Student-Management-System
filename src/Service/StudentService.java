package Service;

import model.Student;
import java.util.*;

/**
 * StudentService demonstrates real-world use of Java Collections:
 *
 *  ArrayList       — master list of all students
 *  HashMap         — fast lookup by student ID
 *  LinkedHashMap   — department-wise grouped students (insertion order)
 *  TreeMap         — grade-wise sorted grouping
 *  HashSet         — unique department names
 *  TreeSet         — top rankers sorted by marks
 *  PriorityQueue   — always get the topper instantly
 *  ArrayDeque      — recently added students (history log)
 *  LinkedList      — waitlist queue (FIFO)
 */
public class StudentService {

    // Master list — ordered, allows iteration
    private final List<Student> studentList = new ArrayList<>();

    // Fast O(1) lookup by ID
    private final Map<Integer, Student> studentById = new HashMap<>();

    // Department → List of students (insertion order preserved)
    private final Map<String, List<Student>> byDepartment = new LinkedHashMap<>();

    // Unique department names
    private final Set<String> departments = new HashSet<>();

    // Top rankers always sorted by marks descending
    private final TreeSet<Student> topRankers = new TreeSet<>();

    // Max-heap: instantly get highest scorer
    private final PriorityQueue<Student> maxHeap =
            new PriorityQueue<>(Comparator.comparingDouble(Student::getMarks).reversed());

    // Recent activity log (last 5 additions)
    private final ArrayDeque<String> activityLog = new ArrayDeque<>();

    // Waitlist as a FIFO Queue
    private final Queue<Student> waitlist = new LinkedList<>();

    // ─────────────────────────────────────────────
    // ADD STUDENT
    // ─────────────────────────────────────────────
    public void addStudent(Student s) {
        if (studentById.containsKey(s.getId())) {
            System.out.println("  [!] Student ID " + s.getId() + " already exists.");
            return;
        }
        studentList.add(s);
        studentById.put(s.getId(), s);
        byDepartment.computeIfAbsent(s.getDepartment(), k -> new ArrayList<>()).add(s);
        departments.add(s.getDepartment());
        topRankers.add(s);
        maxHeap.offer(s);
        logActivity("Added: " + s.getName() + " (" + s.getDepartment() + ")");
        System.out.println("  [+] Added: " + s);
    }

    // ─────────────────────────────────────────────
    // REMOVE STUDENT
    // ─────────────────────────────────────────────
    public void removeStudent(int id) {
        Student s = studentById.remove(id);
        if (s == null) {
            System.out.println("  [!] Student ID " + id + " not found.");
            return;
        }
        studentList.remove(s);
        topRankers.remove(s);
        maxHeap.remove(s);
        List<Student> deptList = byDepartment.get(s.getDepartment());
        if (deptList != null) deptList.remove(s);
        logActivity("Removed: " + s.getName());
        System.out.println("  [-] Removed: " + s);
    }

    // ─────────────────────────────────────────────
    // SEARCH BY ID
    // ─────────────────────────────────────────────
    public Student findById(int id) {
        return studentById.get(id);  // O(1)
    }

    // ─────────────────────────────────────────────
    // SEARCH BY NAME (partial, case-insensitive)
    // ─────────────────────────────────────────────
    public List<Student> searchByName(String keyword) {
        String kw = keyword.toLowerCase();
        List<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getName().toLowerCase().contains(kw)) result.add(s);
        }
        return result;
    }

    // ─────────────────────────────────────────────
    // UPDATE MARKS
    // ─────────────────────────────────────────────
    public void updateMarks(int id, double newMarks) {
        Student s = studentById.get(id);
        if (s == null) { System.out.println("  [!] Not found."); return; }
        topRankers.remove(s);
        maxHeap.remove(s);
        s.setMarks(newMarks);
        topRankers.add(s);
        maxHeap.offer(s);
        logActivity("Updated marks: " + s.getName() + " → " + newMarks);
        System.out.println("  [~] Updated: " + s);
    }

    // ─────────────────────────────────────────────
    // GET TOPPER (O(1) using max-heap)
    // ─────────────────────────────────────────────
    public Student getTopper() {
        return maxHeap.peek();
    }

    // ─────────────────────────────────────────────
    // TOP N STUDENTS (TreeSet — already sorted)
    // ─────────────────────────────────────────────
    public List<Student> getTopN(int n) {
        List<Student> result = new ArrayList<>();
        int count = 0;
        for (Student s : topRankers) {
            if (count++ >= n) break;
            result.add(s);
        }
        return result;
    }

    // ─────────────────────────────────────────────
    // STUDENTS BY DEPARTMENT
    // ─────────────────────────────────────────────
    public Map<String, List<Student>> getByDepartment() {
        return Collections.unmodifiableMap(byDepartment);
    }

    // ─────────────────────────────────────────────
    // GRADE-WISE GROUPING (TreeMap → sorted by grade)
    // ─────────────────────────────────────────────
    public Map<String, List<Student>> groupByGrade() {
        Map<String, List<Student>> gradeMap = new TreeMap<>();
        for (Student s : studentList) {
            gradeMap.computeIfAbsent(s.getGrade(), k -> new ArrayList<>()).add(s);
        }
        return gradeMap;
    }

    // ─────────────────────────────────────────────
    // DEPARTMENT AVERAGE MARKS (HashMap)
    // ─────────────────────────────────────────────
    public Map<String, Double> departmentAverages() {
        Map<String, Double> avg = new LinkedHashMap<>();
        for (Map.Entry<String, List<Student>> entry : byDepartment.entrySet()) {
            double average = entry.getValue().stream()
                    .mapToDouble(Student::getMarks).average().orElse(0);
            avg.put(entry.getKey(), Math.round(average * 10.0) / 10.0);
        }
        return avg;
    }

    // ─────────────────────────────────────────────
    // SORT — uses Collections.sort + Comparator
    // ─────────────────────────────────────────────
    public List<Student> sortByName() {
        List<Student> sorted = new ArrayList<>(studentList);
        sorted.sort(Comparator.comparing(Student::getName));
        return sorted;
    }

    public List<Student> sortByMarks() {
        List<Student> sorted = new ArrayList<>(studentList);
        Collections.sort(sorted); // uses compareTo (marks desc)
        return sorted;
    }

    // ─────────────────────────────────────────────
    // WAITLIST (Queue — FIFO)
    // ─────────────────────────────────────────────
    public void addToWaitlist(Student s) {
        waitlist.offer(s);
        logActivity("Waitlisted: " + s.getName());
        System.out.println("  [W] Added to waitlist: " + s.getName());
    }

    public void admitFromWaitlist() {
        Student s = waitlist.poll();
        if (s == null) { System.out.println("  [!] Waitlist is empty."); return; }
        System.out.println("  [W] Admitting from waitlist: " + s.getName());
        addStudent(s);
    }

    public int waitlistSize() { return waitlist.size(); }

    // ─────────────────────────────────────────────
    // ACTIVITY LOG (ArrayDeque — keeps last 5)
    // ─────────────────────────────────────────────
    private void logActivity(String msg) {
        if (activityLog.size() >= 5) activityLog.pollFirst();
        activityLog.offerLast(msg);
    }

    public ArrayDeque<String> getActivityLog() {
        return new ArrayDeque<>(activityLog);
    }

    // ─────────────────────────────────────────────
    // STATS
    // ─────────────────────────────────────────────
    public int totalStudents()      { return studentList.size(); }
    public Set<String> getDepartments() { return Collections.unmodifiableSet(departments); }
    public List<Student> getAllStudents() { return Collections.unmodifiableList(studentList); }
}
