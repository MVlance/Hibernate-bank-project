package bank;


import bank.entity.Card;
import bank.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AddNewCards {
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
            Card card = new Card("4240368249365062", 0);
            session.persist(card);
            session.getTransaction().commit();
        } finally{
            assert session != null;
            session.close();
            factory.close();
        }
    }
}
