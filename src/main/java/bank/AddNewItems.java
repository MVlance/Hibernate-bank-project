package bank;


import bank.entity.Card;
import bank.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AddNewItems {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(Card.class)
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();

        Session session = null;

        try{
            session = factory.getCurrentSession();
            session.beginTransaction();
            Item item = new Item("headphones", 150);
            session.persist(item);
            session.getTransaction().commit();
        } finally{
            assert session != null;
            session.close();
            factory.close();
        }
    }
}
