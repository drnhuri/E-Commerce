package service;

import dto.CustomerDto;
import entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    // Müşteri eklemek için servis metodu
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    // E-posta ile müşteri bilgilerini almak için servis metodu
    public CustomerDto getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.map(value -> modelMapper.map(value, CustomerDto.class)).orElse(null);
    }

    // Müşteri güncelleme metodu
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setFullName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            Customer updatedCustomer = customerRepository.save(customer);
            return modelMapper.map(updatedCustomer, CustomerDto.class);
        }
        return null;
    }
}
