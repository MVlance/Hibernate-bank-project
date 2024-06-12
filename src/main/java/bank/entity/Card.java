package bank.entity;

import jakarta.persistence.*;

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
}
