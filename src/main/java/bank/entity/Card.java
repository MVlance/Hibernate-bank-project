package bank.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cards")
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "balance")
    private double balance;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE
            , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "cards_items"
            , joinColumns = @JoinColumn(name = "card_id")
            , inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    public Card() {
    }

    public Card(String card_number, double balance) {
        this.cardNumber = card_number;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setId(int id) {}
    public int getId() {
        return id;
    }

    public void setCardNumber(String card_number) {
        this.cardNumber = card_number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "Card{" +
                "card_number='" + cardNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void addItemToCard(Item item){
        if(items == null){
            items = new ArrayList<Item>();
        }
        items.add(item);
    }

    public void removeItemFromCard(Item item){
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean ownsItem(Item item) {
        if (items != null && item != null) {
            for (Item ownedItem : items) {
                if (ownedItem.getId() == item.getId()) {
                    return true; // Found the item in the list
                }
            }
        }
        return false; // Item not found in the list
    }


}
