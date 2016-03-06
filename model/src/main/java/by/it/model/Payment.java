package by.it.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
public class Payment implements Serializable {

    @Id
    @GenericGenerator(name = "orderId", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
    @GeneratedValue(generator = "orderId")
    private long paymentId;

    @Column
    private double amount;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePayment;

    //это подчиненная таблица
    @OneToOne(fetch = FetchType.LAZY)//@Fetch(value = FetchMode.SELECT) 
    @PrimaryKeyJoinColumn//(name = "idP",referencedColumnName = "idE1")
    private PayOrder order;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public PayOrder getOrder() {
        return order;
    }

    public void setOrder(PayOrder order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "paymentId=" + this.getPaymentId() + ", amount=" + this.getAmount() + ", datePayment=" + this.getDatePayment();
    }

}
