package by.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.dao.PayOrderDao;
import by.it.model.PayOrder;
import by.it.model.User;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("payOrderService")
@Transactional
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PayOrderDao payOrderDao;

    public void persist(PayOrder payOrder) {
        payOrderDao.persist(payOrder);
    }

    public void delete(PayOrder payOrder) {
        payOrderDao.delete(payOrder);
    }

    public List<PayOrder> getAll() {
        return payOrderDao.getAll();
    }

    public void saveNewPayOrder(User user, double price) {

        PayOrder payOrder = new PayOrder();
        user.getOrders().add(payOrder);

        payOrder.setUser(user);
        payOrder.setPrice(price);
        payOrder.setDateOpen(new Date());
        payOrder.setNum(getRandomNumb(5));

        persist(payOrder);
    }

    public PayOrder findById(long id) {
        return payOrderDao.getByKey(id);
    }

    private String getRandomNumb(int top) {

        Random ran = new Random();

        char data = ' ';
        String str = "";

        for (int i = 0; i <= top; i++) {
            data = (char) (ran.nextInt(25) + 97);
            str = data + str;
        }
        return str;

    }
}
