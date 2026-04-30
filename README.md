# Student Management System
### Java Collections Framework — Real World Project

## Collections used

| Collection | Where used | Why |
|---|---|---|
| `ArrayList` | Master student list | Ordered, fast iteration |
| `HashMap` | Lookup by ID | O(1) get/put |
| `LinkedHashMap` | Department grouping | Insertion order preserved |
| `TreeMap` | Grade-wise grouping | Auto-sorted by grade key |
| `HashSet` | Unique departments | No duplicates, fast lookup |
| `TreeSet` | Top rankers | Auto-sorted by marks |
| `PriorityQueue` | Instant topper access | O(1) peek, max-heap |
| `ArrayDeque` | Activity log (last 5) | Sliding window log |
| `LinkedList` | Waitlist | FIFO queue |

## Project structure

```
StudentManagement/
└── src/
    ├── Main.java                  ← entry point, all demos
    ├── model/
    │   └── Student.java           ← data model (Comparable)
    ├── service/
    │   └── StudentService.java    ← all collection logic
    └── util/
        └── PrintUtil.java         ← formatted output helpers
```

## How to run

```bash
# Compile
javac -d out src/model/Student.java src/service/StudentService.java src/util/PrintUtil.java src/Main.java

# Run
java -cp out Main
```

## Features demonstrated

1. Add / Remove students with duplicate ID check
2. O(1) lookup by ID using HashMap
3. Partial name search (case-insensitive)
4. Sort by name (Comparator) and by marks (Comparable)
5. Instant topper via PriorityQueue max-heap
6. Top N rankers via TreeSet
7. Department-wise grouping (LinkedHashMap)
8. Grade-wise grouping (TreeMap — sorted A → F)
9. Department average marks
10. Update marks — TreeSet + PriorityQueue re-insertion
11. Waitlist queue — FIFO admit (LinkedList)
12. Activity log — sliding window of last 5 events (ArrayDeque)
