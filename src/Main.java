//Mykyta Danets 19228
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        //----------------------------------------------------------------------------------------------
        //Task2
        Person person = new Person(
                "Mykyta Danets",
                20
        );
        Student studentFirst = new Student(
                "Annie",
                20,
                new int[]{50, 75, 88},
                new String[]{"Math", "Physics"}
        );
        Student studentSecond = new Student(
                "John",
                19,
                new int[]{11, 83, 91},
                new String[]{"Math", "Physics"}
        );
        Teacher teacher = new Teacher(
                "Mr. Bond",
                50,
                4210.97,
                new String[]{"Math", "Computer Science"}
        );

        System.out.println("Person: " + person.name + ", age " + person.age);

        System.out.println("\nStudent: " + studentFirst.name + ", age " + studentFirst.age);
        System.out.println("Grades:");
        for (int g : studentFirst.grades) System.out.println("- " + g);
        System.out.println("Attended:");
        for (String subject : studentFirst.subjectsAttended) System.out.println("- " + subject);

        System.out.println("\nTeacher: " + teacher.name + ", age " + teacher.age + ", salary " + teacher.salary);
        System.out.println("Teaches:");
        for (String subject : teacher.subjectsTaught) System.out.println("- " + subject);

        int person_Teacher = ageOrHighestGrade(person,teacher);
        System.out.println("\nResult of the ageOrHighestGrade method: " + person_Teacher);

        int student_Student = ageOrHighestGrade(studentFirst,studentSecond);
        System.out.println("\nResult of the ageOrHighestGrade method: " + student_Student);

        //------------------------------------------------------------------------------------------------
        //Task 4.5
        List<Person> persons = new ArrayList<>();

        persons.add(studentFirst);
        persons.add(studentSecond);
        persons.add(new Student("Sara", 18, new int[]{77, 82, 90}, new String[]{"Biology"}));
        persons.add(teacher);
        persons.add(new Teacher("Mrs. Green", 55, 3999.50, new String[]{"History"}));

        String filename = "persons.bin";

        savePersonsToFile(persons, filename);

        List<Person> loaded = loadPersonsFromFile(filename);

        System.out.println("\n--- Loaded Persons ---");
        for (Person personToPrint : loaded) {
            printPerson(personToPrint);
        }
    }
    //Task2
    //accept two persons, returns age of 1st + 2nd, if 2 students find highest grade
    public static int ageOrHighestGrade(Person person1,Person person2) {
        int result = 0;
        if (person1 instanceof Student && person2 instanceof Student) {
            Student student1 = (Student) person1;
            Student student2 = (Student) person2;

            for (int g : student1.grades) {
                if (g > result) result = g;
            }

            for (int g : student2.grades) {
                if (g > result) result = g;
            }
        } else{
            result = person1.age + person2.age;
        }

        return  result;
    }
    //Task4.5
    public static void printPerson(Person p) {
        System.out.println("\nPerson: " + p.name + ", age " + p.age);

        if (p instanceof Student s) {
            System.out.println("Grades: " + Arrays.toString(s.grades));
            System.out.println("Attended: " + Arrays.toString(s.subjectsAttended));
        }
        if (p instanceof Teacher t) {
            System.out.println("Salary: " + t.salary);
            System.out.println("Teaches: " + Arrays.toString(t.subjectsTaught));
        }
    }

    public static void savePersonsToFile(List<Person> persons, String filename) {
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(persons);
            System.out.println("\nSaved persons to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving persons: " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.err.println("Error closing output stream: " + e.getMessage());
                }
            }
        }
    }


    public static List<Person> loadPersonsFromFile(String filename) {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            return (List<Person>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading persons: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.err.println("Error closing input stream: " + e.getMessage());
                }
            }
        }
    }

}
class Person implements Serializable {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
class Student extends Person implements Serializable {
    int[] grades;
    String[] subjectsAttended;

    Student(String name, int age, int[] grades, String[] subjectsAttended) {
        super(name, age);
        this.grades = grades;
        this.subjectsAttended = subjectsAttended;
    }
}
class Teacher extends Person implements Serializable {
    double salary;
    String[] subjectsTaught;

    Teacher(String name, int age, double salary, String[] subjectsTaught) {
        super(name, age);
        this.salary = salary;
        this.subjectsTaught = subjectsTaught;
    }
}
