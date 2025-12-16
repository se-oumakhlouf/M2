# SOLID principles :

- **Single-Responsibility Principle :**

```java
// A class should have only one responsibility
// If a class has multiple responsibilities, it should be refactored and other objects should be introduced

class Student {
  private final String firstName;
  private final String lastName;
  private final int streetNumber;
  private final String streetName;
  private final String city;

  // 
  public String name() {/*..*/}

  public String printAddress() {/*..*/}
}

// Could be refactored to :
class Student {
  private final String firstName;
  private final String lastName;
  private final Address address;

  public String name() {/*..*/}

  public String printAddress() {
    return address.print();
  }
}

class Address {
  private final int streetNumber;
  private final String streetName;
  private final String city;

  public String print() {/*..*/}
}
```

---

- **Open-closed Principle :**

```java
// A class should be open for extension but closed for modification

class Student {
  private final List<Double> grades;

  double getScore(GradeStrategy strategy) {
    return strategy.compute(grades);
  }
}

@FunctionalInterface
interface GradeStrategy {
  double compute(List<Double> grades);
}

// Design pattern : Strategy
class AverageGradeStrategy implements GradeStrategy {
  private final int maxNbGrades;

  public AverageGradeStrategy(int maxNbGrades) {
    this.maxNbGrades = maxNbGrades;
  }

  @Override
  public double compute(List<Double> grades) {
    return grades.stream()
               .limit(maxNbGrades)
               .mapToDouble(Double::doubleValue)
               .average()
               .orElse(0);
  }


}
```

---

- **Liskov Substitution Principle :**

```java
// Derived classes should be substitutable for their base classes

class Employee {
  private int salary;

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public double getSalary() {
    return salary;
  }
}

class Manager extends Employee {
  @Override
  public double getSalary() {
    return salary * 1.3;
  }
}

class Application {
  static void main(String[] args) {
    Employee employee = new Employee();
    employee.setSalary(1000);
    System.out.println(employee.getSalary()); // 1000

    Manager manager = new Manager();
    manager.setSalary(1000);
    System.out.println(manager.getSalary()); // 1300
    // Should be 1000
    // Because Manager is a subclass of Employee, but it doesn't have the same behavior
  }
}
```

---

- **Interface Segregation Principle :**

```java
// Clients should not be forced to depend on methods they do not use

interface FlyingAnimals {
  String eat();

  String fly();
}

class Bird implements FlyingAnimals {
  @Override
  public String eat() {
    return "chirp chirp";
  }

  @Override
  public String fly() {
    return "I can";
  }

}

class Dog implements FlyingAnimals {
  @Override
  public String eat() {
    return "woof woof";
  }

  @Override
  public String fly() {
    throw new UnsupportedOperationException("I can't fly");
  }
  // Dog can't fly, but it still implements FlyingAnimals interface
  // Should refactor into 2 interfaces: Animals and FlyingAnimals
}
```

---

- **Dependency Inversion Principle :**

```java
// Code should depend on abstractions, not on concretes classes
// Which means depending on interfaces

interface Shape {
  void draw();
}

class Rectangle implements Shape {
  @Override
  public void draw() {/*..*/}
}

class Circle implements Shape {
  @Override
  public void draw() {/*..*/}
}

class Drawer {
  public void draw(Shape shape) {
    shape.draw();
  }
}
```

---

<br>

# Design  Patterns

--- 

- **Adapter :**

```java
// Makes it possible to use multiple libraries with the same interface while being transparent for the client

// libraries :
class GoogleSMS {
  public void sendSMS(String message) {/*..*/}

  public void sendSMSBulk(String message, List<String> phoneNumbers) {/*..*/}
}

class AmazonSMS {
  public void sendSMS(String message) {/*..*/}

  public void spamSMS(List<String> messages) {/*..*/}

  public void currentPhoneNumber(String phoneNumber) {/*..*/}
}

// gives us the new interface :
interface SMSSender {
  void sendSMS(String message, String phoneNumber);

  default void sendSMSBulk(String message, List<String> phoneNumbers) {
    for (String phoneNumber : phoneNumbers) sendSMS(message, phoneNumber);
  }

  default void sendSMSBulk(List<String> messages, String phoneNumber) {
    for (String message : messages) sendSMS(message, phoneNumber);
  }
}

// and the adapters :
class GoogleSMSAdapter implements SMSSender {
  private final GoogleSMS googleSMS = new GoogleSMS();

  @Override
  public void sendSMS(String message, String phoneNumber) {
    googleSMS.sendSMS(message, phoneNumber);
  }

  @Override
  public void sendSMSBulk(String message, List<String> phoneNumbers) {
    googleSMS.sendSMSBulk(message, phoneNumbers);
  }
}

class AmazonSMSAdapter implements SMSSender {
  private final AmazonSMS amazonSMS = new AmazonSMS();

  @Override
  public void sendSMS(String message, String phoneNumber) {
    amazonSMS.sendSMS(message, phoneNumber);
  }
}
```

---

- **Builder :**

```java
// Convenience builder for complex objects with optional parameters
// We often put the original object in a private field and return it from the builder

import java.util.Objects;

public class Student {
  private final String firstName;
  private final String lastName;

  public static class StudentBuilder {
    public String firstName;
    public String lastName;

    public StudentBuilder firstName(String firstName) {
      this.firstName = Objects.requireNonNull(firstName);
      return this;
    }

    public StudentBuilder lastName(String lastName) {
      this.lastName = Objects.requireNonNull(lastName);
      return this;
    }

    public Student build() {
      return new Student(this);
    }
  }

  public static StudentBuilder with() {
    return new StudentBuilder();
  }

  private Student(StudentBuilder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
  }
}

// Gof Builder pattern :

public interface ReportBuilder {
  ReportBuilder addTitle(String title);

  ReportBuilder addParagraph(String paragraph);

  ReportBuilder addSignature(String signature);

  Report build();
}

public class HTMLReportBuilder implements ReportBuilder {
  private final StringBuilder report = new StringBuilder();

  @Override
  public ReportBuilder addTitle(String title) {
    report.append("<h1>").append(title).append("</h1>");
    return this;
  }

  // ...

  @Override
  public Report build() {
    return new HTMLReport(report.toString());
  }
}
```

---

- **Strategy :**

```java
// Allows changing of the algorithm at runtime

@FunctionalInterface
public interface Strategy {
  double compute(List<Double> grades);
}

public class AverageGradeStrategy implements Strategy {
  private final int maxNbGrades;

  public AverageGradeStrategy(int maxNbGrades) {
    this.maxNbGrades = maxNbGrades;
  }

  @Override
  public double compute(List<Double> grades) {
    return grades.stream()
               .limit(maxNbGrades)
               .mapToDouble(Double::doubleValue)
               .average()
               .orElse(0);
  }
}

public class MinimumGradeStrategy implements Strategy {
  @Override
  public double compute(List<Double> grades) {
    return grades.stream()
               .min(Double::compare)
               .orElse(0);
  }
}

public class Student {
  private final List<Double> grades;

  public double computeGrades(Strategy strategy) {
    return strategy.compute(grades);
  }
}

// -> Open-close principle
// Open for extension / Closed for modification
```

---

- **Singleton :**

```java
// Creates a class that can only have one instance
// Use case: Database connection, Logger, ...

public class Singleton {
  // fields

  private Singleton() {/* initialize fields */}

  private static final Singleton instance = new Singleton();

  public static Singleton getInstance() {
    return instance;
  }
}
```

---

- **Observer :**

```java
// Allows an object to be informed of state changes in other objects

public interface SoccerNews {
  default void onGameStart(Game game) {
  }

  void onGameEnd(Game game);

  void onScoreChange(Game game);
}

public class TwitterNews implements SoccerNews {

  @Override
  public void onGameEnd(Game game) {/*..*/}

  @Override
  public void onScoreChange(Game game) {
    TwitterAPI.tweet(game.homeTeam() + " " + game.homeScore() + " - " + game.awayTeam() + " " + game.awayScore());
  }
}

public class Game {
  private String homeTeam;
  private int homeScore;
  private String awayTeam;
  private int awayScore;
  private final List<SoccerNews> observers = new ArrayList<>();

  public String homeTeam() {/*..*/}

  public int homeScore() {/*..*/}

  public String awayTeam() {/*..*/}

  public int awayScore() {/*..*/}

  // ...

  public void register(SoccerNews observer) {
    observers.add(Objects.requireNonNull(observer));
  }

  public void unregister(SoccerNews observer) {
    observers.remove(observer);
  }

  public void notifyScoreChange() {
    observers.forEach(observer -> observer.onScoreChange(this));
  }

  public void notifyGameEnd() {
    observers.forEach(observer -> observer.onGameEnd(this));
  }
}

```

---

- **Decorator :**

```java
// Adds features to an object without modifying it
// Open-close principle + Single-responsibility principle

// A decorator implements an interface T
// has an object of type T as a field

public interface Dog {
  void bark();
}

public class RegularDog implements Dog {
  @Override
  public void bark() {
    System.out.println("Woof!");
  }
}

public class LoggedDog implements Dog {
  private final Logger logger = Logger.getLogger(LoggedDog.class.getName());
  private final Dog dog;

  public LoggedDog(Dog dog) {
    this.dog = Objects.requireNonNull(dog);
  }

  @Override
  public void bark() {
    Logger.log("Dog barked");
    dog.bark();
  }
}

public class Application {
  static void main(String[] args) {
    Dog dog = new LoggedDog(new RegularDog());
    dog.bark(); // Logs "Dog barked" and then "Woof!"
  }
}
```

---

- **Proxy :**

```java
// Controls access to an object

public interface Database {
  void query(String query);
}

public class RealDatabase implements Database {
  @Override
  public void query(String query) {
    System.out.println("Executing query : " + query);
  }
}

public class SecureDatabaseProxy implements Database {
  private final Database database;
  private final String currentUser;

  public SecureDatabaseProxy(String currentUser) {
    this.database = new RealDatabase();
    this.currentUser = Objects.requireNonNull(currentUser);
  }

  @Override
  public void query(String query) {
    if (currentUser.equals("ADMIN")) database.query(query);
    else throw new SecurityException("Access denied for " + currentUser);
  }
}

public class ThreadSafeDatabaseProxy implements Database {
  private final Database database;

  public ThreadSafeDatabaseProxy(Database database) {
    this.database = Objects.requireNonNull(database);
  }

  @Override
  public synchronized void query(String query) {
    database.query(query);
  }
}

public class Application {
  static void main(String[] args) {
    Database secureDB = new SecureDatabaseProxy("ADMIN");
    Database secureAndTsDB = new ThreadSafeDatabaseProxy(secureDB);
    secureAndTsDB.query("SELECT * FROM users");
  }
}
```

---

- **Composite :**

```java
// Creates a tree structure of objects
// Each node implements the same interface

import java.nio.file.Path;

public sealed interface FileSystem {

  Path path();

  String name();

  record File(Path path, String name, String extension) implements FileSystem {
    // ...
  }

  record Directory(Path path, String name, List<FileSystem> children) implements FileSystem {
    // ...
  }
}
```

---

- **Visitor :**

```java
// Adds features to a class that implements an interface without modifying the interface nor the class

interface Monster {
  void accept(WarriorVisitor guerrier);
}

class Dragon implements Monster {
  @Override
  public void accept(WarriorVisitor guerrier) {
    guerrier.visit(this);
  }
}

class Gobelin implements Monster {
  @Override
  public void accept(WarriorVisitor guerrier) {
    guerrier.visit(this);
  }
}

interface WarriorVisitor {
  void visit(Dragon dragon);
  void visit(Gobelin gobelin);
}

class Warrior implements  WarriorVisitor {
  
  @Override
  public void visit(Dragon dragon) {
    System.out.println("The dragon is to high to attack !");
  }
  
  @Override
  public void visit(Gobelin gobelin) {
    System.out.println("Critical hit on the gobelin !");
  }
}

public class Application {
  static void main(String[] args) {
    List<Monster> monstres = List.of(new Dragon(), new Gobelin());
    WarriorVisitor pantheon = new Warrior();
    monstres.forEach(monster -> {
      System.out.println("Attacking " + monster.getClass().getSimpleName());
      monster.accept(pantheon);
    });
  }
}
```

---

- **Factory :**

```java
/*
    Factory Method Pattern:
      - static factory method (v0, v1)
      - factory (class)
      - abstract factory
      - factory method
 */

// Static factory method (v0)
// Static method that returns an object of a class

public class Line {
  int x1, y1, x2, y2;

  private Line(int x1, int y1, int x2, int y2) {/*..*/}

  public static Line formPointAndVector(int x, int y, int vx, int vy) {
    return new Line(x, y, x + vx, y + vy);
  }

  public static Line fromPoints(Point p1, Point p2) {
    return new Line(p1.x, p1.y, p2.x, p2.y);
  }
}

public record Point(int x, int y) {/*..*/
}


// Static factory method (v1)
// Static method that returns an object of an abstract class

public interface Shape {
  /*..*/
}

public class Line implements Shape {/*..*/
}

public class Rectangle implements Shape {/*..*/
}

public class Application {
  public static Shape createShape(String shapeType) {
    return switch (shapeType) {
      case "LINE" -> new Line();
      case "RECTANGLE" -> new Rectangle();
      default -> throw new IllegalArgumentException("Unknown shape type : " + shapeType);
    };
  }
}


// Factory (class)
// Class that returns an object of a class

public class GoblinFactory {
  private long currentId;
  private final Map<Long, Monster> dungeon = new HashMap<>();
  
  
  public Monster createMonster(String name, int level) {
    Monster monster = new Goblin(currentId++, name, level);
    dungeon.put(monster.currentId(), monster);
    return monster;
  }
  
  public Optional<Monster> findMonsterById(long id) {
    return Optional.ofNullable(dungeon.get(id));
  }
}

public interface Monster {/*..*/}

public class Goblin implements Monster {
  private final long currentId;
  private final String name;
  private final int level;
  
  private Goblin(long id, String name, int level) {/*..*/}
}


// Abstract factory
// Interface in common for multiple factory
// Composed of: - an interface for the constructed objects
//              - an interface for the factories

public interface Weapon {
  void attack();
}

public interface Armor {
  void defend();
}

public class ElfBow implements Weapon {
  @Override
  public void attack() { System.out.println("Elf shoots arrows !"); }
}

public class ElfShield implements Armor {
  @Override
  public void defend() { System.out.println("Elf protects himself !"); }
}

public class OrcAxe implements Weapon {
  @Override
  public void attack () { System.out.println("Orc cuts with his axe !"); }
}

public class OrcHelmet implements Armor {
  @Override
  public void defend() { System.out.println("Orc blocks !"); }
}

interface KingdomFactory {
  Weapon createWeapon();
  Armor createArmor();
}

class ElfKingdomFactory implements KingdomFactory {
  @Override 
  public Weapon createWeapon() { return new ElfBow(); }
  
  @Override 
  public Armor createArmor() { return new ElfShield(); }
}

class OrcKingdomFactory implements KingdomFactory {
  @Override 
  public Weapon createWeapon() { return new OrcAxe(); }
  
  @Override 
  public Armor createArmor() { return new OrcHelmet(); }
}

class Army {
  private final Weapon weapon;
  private final Armor armor;

  public Army(KingdomFactory factory) {
    this.weapon = factory.createWeapon();
    this.armor = factory.createArmor();
  }

  public void fight() {
    weapon.attack();
    armor.defend();
  }
}


// Factory method
// Abstract method of type Factory which will be implemented by the child classes

public abstract class ClassRoomCreator {
  //
  public abstract Student createStudent(String firstName, String lastName);
}

public interface Student {/*..*/}
public record JavaStudent(String firstName, String lastName) implements Student {/*..*/}
public record CStudent(String firstName, String lastName) implements Student {/*..*/}

public class JavaClassRoomCreator extends ClassRoomCreator {
  @Override
  public Student createStudent(String firstName, String lastName) {
    return new JavaStudent(firstName, lastName);
  }
}

public class CClassRoomCreator extends ClassRoomCreator {
  @Override
  public Student createStudent(String firstName, String lastName) {
    return new CStudent(firstName, lastName);
  }
}
```