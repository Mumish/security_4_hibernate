package by.it.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Mumish
 */
@Entity
//TODO: кэш в прошлый раз как-то криво работал. Возвращал коллекцию с ИД но объекты налл
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String num;
    @Column
    private double price;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOpen;
    //TODO:почему hibernate мапит это поле, если не стоит аннотации?
    private int statusId;

    //это подчиненная таблица
    @ManyToOne(fetch = FetchType.LAZY)//@Fetch(value = FetchMode.SELECT) 
    @JoinColumn(name = "F_userId")//,referencedColumnName = "idE1")
    private User user;

    //это главная таблица в связи
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "id=" + this.getId() + ", num=" + this.getNum() + ", price=" + this.getPrice() + ", dateOpen=" + this.getDateOpen() + ", statusId=" + this.getStatusId();
    }

}
