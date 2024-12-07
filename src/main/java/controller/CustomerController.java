package controller;

import dto.CustomerDto;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    // Müşteri ekleme
    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }

    // E-posta ile müşteri bilgilerini alma
    @GetMapping("/email/{email}")
    public CustomerDto getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    // Müşteri güncelleme
    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }
}
