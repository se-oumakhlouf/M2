package fr.uge.ducks;

public class Main {

	public static void main(String[] args) {
//		Duck duck1 = new RegularDuck();
//    duck1.quack();
//    Duck duck2 = new LoggedDuck(new RegularDuck());
//    duck2.quack();
//    duck1.quack();
//    duck2.quack();
		
		Duck duck = new LoggedDuck(new RegularDuck());
    duck.quackManyTimes(2);
    
//    Méthode non default :
//    Cela fait l'appel à LoggedDuck.quackManyTimes, 
//    puis à RegularDuck.quackManyTimes,
//    puis à RegularDuck.quack et non à
//    LoggedDuck.quack, on obtient donc aucun log
    
//    Méthode default :
//    Cela fait un appel à LoggedDuck.quack
//    On obtient donc nos log
//    Ce n'est pas robuste, car on ne peut pas
//    redéfinir la méthode quackManyTmes dans notre LoggedDuck
	}

}
