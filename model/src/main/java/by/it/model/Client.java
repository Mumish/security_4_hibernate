package by.it.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Mumish
 */
@Entity
public class Client implements Serializable {

    /**
     *
     */
    public Client() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.clientId ^ (this.clientId >>> 32));
        hash = 47 * hash + Objects.hashCode(this.fio);
        hash = 47 * hash + Objects.hashCode(this.login);
        hash = 47 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (this.clientId != other.clientId) {
            return false;
        }
        if (!Objects.equals(this.fio, other.fio)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    /**
     * ИД клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientId;

    /**
     * ФИО клиента
     */
    @Column
    private String fio;

    /**
     * Логин клиента
     */
    @Column
    private String login;
    /**
     * Пароль клиента
     */
    @Column
    private String password;
    //это главная таблица в связи
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Account account;
    //это главная таблица в связи
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private CreditCard creditCard;
    //это главная таблица в связи
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PayOrder> orders;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public List<PayOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PayOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "clientId=" + this.getClientId() + ", fio=" + this.getFio() + ", login=" + this.getLogin() + ", password=" + this.getPassword();
    }

}
