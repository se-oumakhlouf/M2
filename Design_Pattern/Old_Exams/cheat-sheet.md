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

# Design  Pattern

--- 

- **Adapter :**

- **Builder :**

- **Strategy :**

- **Singleton :**

- **Observer :**

- **Proxy :**

- **Decorator :**

- **Composite :**

- **Visitor :**

- **Factory :**