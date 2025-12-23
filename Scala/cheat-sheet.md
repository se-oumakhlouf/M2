# Scala

- **Variable :**
    ```scala
    - val // immutable variable
    - var // mutable variable
    ```

- **Type System :**
    ```scala
    - Boolean
    - Byte // signed 8 bits
    - Short // 16 bits
    - Int // 32 bits
    - Long // 64 bits
    - Float // 32 bits simple precision
    - Double // 64 bits double precision
    - Char // 16 bits characters Unicode not signed
    - String // character sequence, fromJava
    ```

- **Control structures :**
    ```scala
    - do {..} while (test: Boolean)
    - while (test: Boolean) {..}
    - for (i <- 1 to 3: Seq[Int]) {..} // 1, 2, 3
    - for (i <- 1 until 3: Seq[Int]) // 1, 2

    for (c <- "hello") println(a)
    // h, e, l, l, o
    ```

- **Option :**
    ```scala
    // Can tanke the value Some(valeur) or None
    def findUser(userId: Int): Option[String] = {
        if (userId = 1) Some("Alice")
        else None
    }
    ```

- **Either :**
    ```scala
    // used when the message of failure is usefull
    // - success with Right
    // - failure with Left

    def divide (a: Int, b: Int): Either[String, Int] = {
        if (b == 0) Left("Division by 0")
        else Right(a / b)
    }
    ```

- **Lazy :**
    ```scala
    object Lazy {lazy val x = {..}} // keyword lazy
    object NotLazy {val x = {..}}
    ```

- **Nested function :**
    ```scala
    def sumOfSquares(x: Int, y: Int): Int = {
        def square(n: Int): Int = n * n
        square(x) + square(y)
    } // the square function is only visible inside the sumOfSquares block
    ```

- **`_` parameter :**
    ```scala
    val increment: Int => Int = _ + 1
    val increment = (x: Int) => x + 1
    ```

- **Partially applied functions :**
    ```scala
    def sum(a: Int, b: Int) = a + b
    val a = sum(5, _: Int)
    a(7) // 12
    ```

- **Function with repeated paramters :**
    ```scala
    def echo (args: String*) = for (arg <- args) println(arg)
    // String* != Array[String]

    echo: (args: String*)Unit
    echo("abc", "def", "ghi")
    echo(Array("abc", "def", "ghi")) // error: type mismatch
    ```

- **Tail recursion :**

    A function is **tail recursive** if its very last action is a call to itself (or another function)

    Benefit : The computer can reuse the stack frame (memory space) for each step, wich saves memory and prevents stack overflow errors

    If a function is tail recursive we can annotate it with **@annotation.tailrec**

    ```scala
    def factoriel(n: Int) = {
        @annotation.tailrec
        def loop(n: Int, acc: Int): Int = {
            if (n == 1) acc
            else loop(n - 1, n * acc) // call to itself
        }
        loop(n, 1)
    }

    def factorielNotTailRec(n: Int): Int = {
        if (n == 1) 1
        else n * factoriel (n - 1) // n * call to itself, is NOT tail rec
    }
    ```

- **Currying :**
    ```scala
    // Replacing structured arguments with a sequence of simple arguments

    def add(x: Int, y: Int) = x + y
    def addCurry (x: Int) (y: Int) = x + y

    add(5, 4)
    addCurry(5)(4)
    ```

- **Classes :**
    ```scala
    class Person(lastName: String, age: Int) // automatic creation of a principal constructor
    
    val p1 = new Person("Toto", 27)
    p1.lastName // error: value lastName is not a member of Person
    // parameters with `val` or `var` are not accessible

    // the main constructor can be private
    class Person private(name: String, age: int)


    class PersonBetter(val lastName: String, var age: Int = 1) // parameters of a class can have default values
    val pb1 = new PersonBetter("tito", 99)
    pb1.lastName // tito
    ```

- **Fields :**
    ```scala
    // private, protected, public (by default)
    class Person (val name: String, private val gender: String, var phoneNumber: String) {
        var age = 1
    }
    // getters and setters will be created automatically for mutable fields (age, phoneNumber)
    ```

- **Auxiliary constructor :**
    ```scala
    class Car(val model String, var color: String) {
        def this(model: String) = this(model, "blue")
        def this() = this("Multipla", "blue")
    }
    val fiat = new Car()
    ```

- **`val` and `def` functions :**
    ```scala
    // Use def-functions to define (class) methods and val functions to define function
    ```

- **High Order Function `HOF` :**
    ```scala
    // Passing a function in another function

    // val functions
    val add: (Int, Int) => Int = (x, y) => x + y
    val apply: (Int, Int, (Int, Int) => Int) => Int = (x, y, f) => f(x, y)

    // def functions
    def add (x: Int, y: Int): Int = x + y
    def apply (x: Int, y: Int, f: (Int, Int) => Int): Int = f(x, y)
    ```

- **Case Classes :**
    ```txt
    - case class :
        - new : Optional
        - apply / unapply methods : Auto generated
        - methods automatically generated : toString, equals, hashCode, copy
        - immutability : Encouraged but not mandatory
        - pattern matching : Support
        - object companion: Auto-generated
        - inheritance : Can extend other classes but "case-to-case inheritance is prohibited"

    - class :
        - new : Mandatory
        - apply / unapply methods : No auto-generation
        - methods automatically generated : None
        - immutability : Not mandatory
        - pattern matching : no direct support
        - object companion : no auto-generation
        - inheritance : can extend other classes
    ```



- **Trait :**
    ```scala
    // similar to Java interfaces
    Trait Animal {
        def sleep = "Zzz" // field with a value
        
        def size: Int // field with no initial value
        def name: String // field with no initial value

        def eat(food: String): String // abstract method
    }

    Trait Felin (val teeth: Int) {
        def scratch(item: String): String
        def sleep = "Purr"
    }

    class Cat (override val teeth: Int) extends Animal with Felin {
        var size = 1
        val name = "Fifo"

        override def sleep = super[Felin].foo // "Purr" 

        def eat (food: String): String = s"The cat $name eats $food"
        def scratch (item: String): String = s"The cat scratched $item"
    }
    ```

- **Sealed Trait :**
    ```scala
    // All trait components must be declared in the same file

    sealed trait Shape
    
    case class Circle(radius: Double) extends Shape
    case class Rectangle(width: Double, height: Double) extends Shape
    case class Square(side: Double) extends Shape
    ```

- **Enum :**
    ```scala
    enum Size :
        case Small, Medium, Large

    def getPrice(s: Size): Int =
        s match {
            case Size.Small => 10
            case Size.Medium => 20
            case Size.Large => 30
        }
    ```