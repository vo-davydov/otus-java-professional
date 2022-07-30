package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> customerMap;

    public CustomerService() {
        this.customerMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> entry = customerMap.firstEntry();
        return new AbstractMap.SimpleEntry<>(new Customer(entry.getKey()), entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return Optional.ofNullable(customerMap.higherEntry(customer))
                .<Map.Entry<Customer, String>>map(e -> new AbstractMap.SimpleEntry<>(new Customer(e.getKey()), e.getValue()))
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
