package bank;

import bank.entity.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Card.class)
                .buildSessionFactory();

        Session session = null;



        try{

            int keyboardPrice = 300;
            int mousePrice = 200;
            int headphonesPrice = 150;




            session = factory.getCurrentSession();

            session.beginTransaction();
            Card card = session.get(Card.class, 1);


            System.out.println("Hello, what do you wanna do next?");
            System.out.println("""
                    1. Buy something
                    2. Sell something
                    3. Make a deposit
                    4. Make a withdrawal
                    """);

            Scanner scanner = new Scanner(System.in);


            switch(scanner.nextInt()){
                case 1:

                    System.out.println("Great!");
                    System.out.println("""
                            Items to buy:
                            1. Keyboard (300)
                            2. Mouse (200)
                            3. Headphones (150)
                            
                            Now choose!
                            """);

                    int item = scanner.nextInt();

                    if(item == 1){
                        card.setBalance(card.getBalance()-keyboardPrice);
                        session.merge(card);
                        System.out.println("You've successfully bought" +
                                " the keyboard! Your current" +
                                " balance is " + card.getBalance());
                    } else if(item == 2){
                        card.setBalance(card.getBalance()-mousePrice);
                        session.merge(card);
                        System.out.println("You've successfully bought" +
                                " the mouse! Your current " +
                                "balance is " + card.getBalance());
                    } else if(item == 3){
                        card.setBalance(card.getBalance()-headphonesPrice);
                        session.merge(card);
                        System.out.println("You've successfully bought" +
                                " the headphones! Your current" +
                                " balance is " + card.getBalance());
                    } else{
                        System.out.println("there's no item under the id " + item);
                    }
                    break;

                case 2:
                    System.out.println("Great!");

                    System.out.println("""
                            Items to sell:
                            1. Keyboard (300)
                            2. Mouse (200)
                            3. Headphones (150)
                            
                            Now choose!
                            """);

                    item = scanner.nextInt();
                    if(item == 1){
                        card.setBalance(card.getBalance()+keyboardPrice);
                        System.out.println("You've successfully sold" +
                                " the keyboard! Your current" +
                                " balance is " + card.getBalance());
                    } else if(item == 2){
                        card.setBalance(card.getBalance()+mousePrice);
                        session.merge(card);
                        System.out.println("You've successfully sold" +
                                " the mouse! Your current" +
                                " balance is " + card.getBalance());
                    } else if(item == 3){
                        card.setBalance(card.getBalance()+headphonesPrice);
                        session.merge(card);
                        System.out.println("You've successfully sold" +
                                " the headphones! Your current" +
                                " balance is " + card.getBalance());
                    } else{
                        System.out.println("there's no item under the id " + item);
                    }
                    break;


                case 3:
                    System.out.println("Great!");

                    System.out.println("How much money do you want to deposit?");

                    int deposit = scanner.nextInt();
                    card.setBalance(card.getBalance()+deposit);
                    System.out.println("Great! Your current balance is " + card.getBalance());

                    break;


                case 4:
                    System.out.println("Great!");

                    System.out.println("How much money do you want to withdraw?");
                    int withdraw = scanner.nextInt();
                    card.setBalance(card.getBalance()-withdraw);
                    System.out.println("Great! Your current balance is " + card.getBalance());
                    break;

            }

            session.getTransaction().commit();

        } finally {
            if (session != null) {
                session.close();
            }
            factory.close();
        }


    }
}