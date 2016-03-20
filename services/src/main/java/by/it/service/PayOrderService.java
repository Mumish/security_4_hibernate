package by.it.service;

import by.it.model.PayOrder;

public interface PayOrderService extends DataService<PayOrder> {

    void saveNewPayOrder(PayOrder order);

}
