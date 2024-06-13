package bank;

import bank.entity.Card;
import bank.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Card.class)
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();

        Session session = null;



        try{

            session = factory.getCurrentSession();

            session.beginTransaction();


            System.out.println("Hello! What's your card number?");
            Scanner scanner = new Scanner(System.in);
            String cardNumber = scanner.nextLine();


            //getting card from database
            List<Card> cards = session.createQuery("from Card "
            + " where cardNumber = " + "'" + cardNumber + "'", Card.class).getResultList();


            //checking whether there's the card with this card number
            if(cards.isEmpty()){
                System.out.println("You typed the wrong card number");
            }

            //getting the needed card
            Card card = cards.getFirst();


            System.out.println("What do you wanna do next?");
            System.out.println("""
                    1. Buy something
                    2. Sell something
                    3. Make a deposit
                    4. Make a withdrawal
                    """); //choice

            List<Item> items = session.
                    createQuery("from Item", Item.class).getResultList();  //getting all the items from the database


            switch(scanner.nextInt()){    //takes number from 1 to 4 from that choice
                case 1:


                    System.out.println("Great!");
                    System.out.println("Items to buy: ");
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println(i+1 + ". " + items.get(i).getName()); //prints all items in the database
                    }

                    int item = scanner.nextInt(); //choice

                    //checking whether enough money on the balance
                    if(card.getBalance() < items.get(item-1).getPrice()){
                        System.out.println("Sorry, you don't have enough money");
                    } else {
                        //to calculate the current balance
                        card.setBalance(card.getBalance() - items.get(item-1).getPrice());

                        card.addItemToCard(items.get(item-1)); //adding item to card
                        session.merge(card); //updating card
                        System.out.println("Successfully bought a " + items.get(item-1).getName());
                        break;
                    }


                case 2:

                    System.out.println("Great!");
                    System.out.println("Items to sell: ");
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println(i+1 + ". " + items.get(i).getName()); //prints all items in the database
                    }

                    item = scanner.nextInt(); //choice


                    if(card.ownsItem(items.get(item-1))){
                        //calculate the current balance
                        card.setBalance(card.getBalance() + items.get(item-1).getPrice());

                        card.removeItemFromCard(items.get(item-1));
                        session.merge(card);

                        System.out.println("Successfully sold a " + items.get(item-1).getName());

                        break;
                    } else{
                        System.out.println("Sorry, you don't own that item");
                        break;
                    }



                case 3:
                    System.out.println("Great!");

                    System.out.println("How much money do you want to deposit?");

                    int deposit = scanner.nextInt(); //choice
                    card.setBalance(card.getBalance()+deposit); //deposit
                    System.out.println("Great! Your current balance is " + card.getBalance());

                    break;


                case 4:
                    System.out.println("Great!");

                    System.out.println("How much money do you want to withdraw?");
                    int withdraw = scanner.nextInt(); //choice
                    card.setBalance(card.getBalance()-withdraw); //withdraw
                    System.out.println("Great! Your current balance is " + card.getBalance());
                    break;

                default:
                    //if number isn't between 1 and 4
                    System.out.println("You have to type a number between 1 and 4!");
                    break;
            }

            session.getTransaction().commit();

        } finally {    //closing session and factory
            if (session != null) {
                session.close();
            }
            factory.close();
        }


    }
}