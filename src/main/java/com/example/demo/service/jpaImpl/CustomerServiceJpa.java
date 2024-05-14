package com.example.demo.service.jpaImpl;

import com.example.demo.entities.Beer;
import com.example.demo.entities.Customer;
import com.example.demo.mappers.CustomerMapper;
import com.example.demo.model.CustomerDTO;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJpa implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer =customerMapper.customerToCustomerDto( customerRepository.
                save(customerMapper.customerDtoToCustomer(customer)));

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);

        if(existingCustomer.isPresent()){
            var foundCustomer = existingCustomer.get();
            foundCustomer.setName(customer.getName());

            return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer)));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
