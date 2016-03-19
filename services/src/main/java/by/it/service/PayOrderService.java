package by.it.service;

import by.it.model.PayOrder;
import by.it.model.User;

public interface PayOrderService extends DataService<PayOrder> {

    void saveNewPayOrder(User user, double price);

}
