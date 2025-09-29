```scala
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]
```

---

`sealed` permet de définir une liste fermée des implémentations d'un `trait`

---

- `case` permet d'obtenir les méthodes par défaut suivantes : `apply`, `copy`
- `copy` est très utile ici pour créer de nouvelle instance des objets facilement

---

le type générique avec un `+` (`[+A]`) signifie que l'on souhaite obtenir un sous-type de `[A]`

---

`Nothing` rend possible une implémentation générique et sûr des listes