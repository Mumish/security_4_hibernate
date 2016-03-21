package by.it.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author Mumish
 */
@Entity
public class CreditCard implements Serializable {

    @Id
    @GenericGenerator(name = "id", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "id")
    private long creditCardId;
    @Column
    private String num;
    @Column
    private double balance;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOpen;
    //TODO:почему hibernate мапит это поле, если не стоит аннотации?
    private int statusId;

    //это подчиненная таблица
    @OneToOne(fetch = FetchType.EAGER)//@Fetch(value = FetchMode.SELECT) 
    @PrimaryKeyJoinColumn//(name = "idP",referencedColumnName = "idE1")
    private User user;

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(Date dateOpen) {
        this.dateOpen = dateOpen;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "creditCardId=" + this.getCreditCardId() + ", num=" + this.getNum() + ", balance=" + this.getBalance() + ", dateOpen=" + this.getDateOpen() + ", statusId=" + this.getStatusId();
    }

}
