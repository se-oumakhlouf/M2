# SOLID principles :

- **Single-Responsibility Principle :**
```java
// A class should have only one responsibility
// If a class has multiple responsibilities, it should be refactored and other objects shoduld be introduced

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
  public String printAddress() {return address.print();}
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
// A class should be open for extension, but closed for modification

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
  
  public AverageGradeStrategy(int maxNbGrades) {this.maxNbGrades = maxNbGrades;}
  
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
  public void setSalary(int salary) {this.salary = salary;}
  public double getSalary() {return salary;}
}

class Manager extends Employee {
  @Override
  public double getSalary() {return salary * 1.3;}
}

class Application {
  public static void main(String[] args) {
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
  public String eat () {return "chirp chirp";}
  
  @Override
  public String fly() {return "I can";}

}

class Dog implements FlyingAnimals {
  @Override
  public String eat() {return "wouf wouf";}
  
  @Override
  public String fly() {throw new UnsupportedOperationException("I can't fly");}
  // Dog can't fly, but it still implements FlyingAnimals interface
  // Should refactor into 2 interfaces : Animals and FlyingAnimals
}
```

---

- **Dependency Inversion Principle :**
```java
// Code should depend on abstractions, not on concretes classes
// Which means depending on interfaces

interface Shape{
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
  public void draw(Shape shape) {shape.draw();}
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
  
  default public void sendSMSBulk(String message, List<String> phoneNumbers) {
    for (String phoneNumber : phoneNumbers) sendSMS(message, phoneNumber);
  }
  
  default public void sendSMSBulk(List<String> messages, String phoneNumber) {
    for (String message : messages) sendSMS(message, phoneNumber);
  }
}

// and the adapters :
class GoogleSMSAdapter implements SMSSender {
  private final GoogleSMS googleSMS = new GoogleSMS();
  
  @Override
  public void sendSMS(String message, String phoneNumber) {googleSMS.sendSMS(message, phoneNumber);}

  @Override
  public void sendSMSBulk(String message, List<String> phoneNumbers) {googleSMS.sendSMSBulk(message, phoneNumbers);}
}

class AmazonSMSAdapter implements SMSSender {
  private final AmazonSMS amazonSMS = new AmazonSMS();
  
  @Override
  public void sendSMS(String message, String phoneNumber) {amazonSMS.sendSMS(message, phoneNumber);}
}
```

---

- **Builder :**

```java
// Convenience builder for complex objects with optional parameters
// We often put the original object in a private field and return it from the builder

import java.util.Objects;

class Student {
  private final String firstName;
  private final String lastName;

  public static class StudentBuilder {
    public String firstName;
    public String lastName;

    public StudentBuilder firstName (String firstName) {
      this.firstName = Objects.requireNonNull(firstName);
      return this;
    }
    
    public StudentBuilder lastName (String lastName) {
      this.lastName = Objects.requireNonNull(lastName);
      return this;
    }

    public Student build () {
      return new Student(this);
    }
  }
  
  public static StudentBuilder with () {return new StudentBuilder();}

  private Student (StudentBuilder builder) {
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
  public Report build() {return new HTMLReport(report.toString());}
}
```

---

- **Strategy :**
```java
// Allows to change the algorithm at runtime

@FunctionalInterface
public interface Strategy {
  double compute(List<Double> grades);
}

public class AverageGradeStrategy implements Strategy {
  private final int maxNbGrades;
  
  public AverageGradeStrategy(int maxNbGrades) {this.maxNbGrades = maxNbGrades;}
  
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
        .orElse(0);}
}

public class Student {
  private final List<Double> grades;
    
  public double computeGrades(Strategy strategy) {return strategy.compute(grades);}
}

// -> Open-close principle
// Open for extension / Closed for modification
```

---

- **Singleton :**
```java
// Creates a class that can only have one instance
// Use case : Database connection, Logger, ...

public class Singleton {
  // fields
  
  private Singleton() {/* initialize fields */}
  
  private static final Singleton instance = new Singleton();
  public static Singleton getInstance() {return instance;}
}
```

---

- **Observer :**
```java
// Allows an object to be informed of state changes in other objects

public interface SoccerNews {
  default void onGameStart(Game game) {}
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
  
  public void register(SoccerNews observer) {observers.add(Objects.requireNonNull(observer));}
  
  public void unregister(SoccerNews observer) {observers.remove(observer);}
  
  public void notifyScoreChange() {observers.forEach(observer -> observer.onScoreChange(this));}
  
  public void notifyGameEnd() {observers.forEach(observer -> observer.onGameEnd(this));}
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
  public void bark() {System.out.println("Woof!");}
}

public class LoggedDog implements Dog {
  private final Logger logger = Logger.getLogger(LoggedDog.class.getName());
  private final Dog dog;

  public LoggedDog (Dog dog) {
    this.dog = Objects.requireNonNull(dog);
  }

  @Override
  public void bark(){
    Logger.log("Dog barked");
    dog.bark();
  }
}

public class Application {
  static void main (String[] args) {
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

  public SecureDatabaseProxy (String currentUser) {
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
  
  public ThreadSafeDatabaseProxy (Database database) {
    this.database = Objects.requireNonNull(database);
  }
  
  @Override
  public synchronized void query(String query) {
    database.query(query);
  }
}

public class Application {
  static void main (String[] args) {
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



```

- **Factory :**