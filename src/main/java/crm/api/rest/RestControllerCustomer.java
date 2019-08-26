package crm.api.rest;

import crm.api.entity.Customer;
import crm.api.rest.exceptions.RestExceptionNotFound;
import crm.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerCustomer {

    // autowire the CustomerService (injecting dependency)
    @Autowired
    private CustomerService customerService;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // add mapping for GET / customers
    @GetMapping("/customers/list")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    // add mapping for GET / customers / {customerId}
    @GetMapping("/customers/list/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        Customer theCustomer = customerService.getCustomer(customerId);

        // a) if Id == Int but not found in DB -> theCustomer = null
        // b) if If != Int -> Error 500 (without ExceptionHandler)
        // for both options we use build entity: RestExceptionEntity

        // option (a): RestExceptionNotFound -> CustomerRestExceptionHandler -> #404 Not Found
        if (theCustomer == null) {
            throw new RestExceptionNotFound("Customer with id = " + customerId + " not found.");
        }

        // option (b) -> directly to CustomerRestExceptionHandler -> #400 Bad Request

        return theCustomer;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // add mapping for POST / customers - add new customer
    @PostMapping("/customers/new")
    public Customer addCustomer(@RequestBody Customer theCustomer) throws Exception {

        // setting Id to "0" or "null" means that we want DAO to create a NEW item (not update existing)
        theCustomer.setId(0);

        // TODO: we can add email validation (optional)
        return customerValidation(theCustomer);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // add mapping for PUT / customers - update existing customer
    @PutMapping("/customers/update")
    public Customer updateCustomer(@RequestBody Customer theCustomer) throws Exception {

        // TODO: we can add email validation (optional)
        return customerValidation(theCustomer);
    }

    private Customer customerValidation(@RequestBody Customer theCustomer) throws Exception {
        if (theCustomer.getFirstName() == null) throw new Exception("First name is required");
        if (theCustomer.getFirstName() == "") throw new Exception("First name can not be empty!");
        if (theCustomer.getLastName() == null) throw new Exception("Last name is required");
        if (theCustomer.getLastName() == "") throw new Exception("Last name can not be empty!");
        if (theCustomer.getEmail() == null) throw new Exception("Email is required");
        if (theCustomer.getEmail() == "") throw new Exception("Email can not be empty!");

        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // add mapping for DELETE / customers - delete existing customer
    @DeleteMapping("/customers/delete/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) throws Exception {

        //checking if customer with Id exists
        Customer tempCustomer = customerService.getCustomer(customerId);
        if (tempCustomer == null) {
            throw new RestExceptionNotFound("Customer with id = " + customerId + " not found.");
        }

        customerService.deleteCustomer(customerId);

        return "Deleted customer id: " + customerId;
    }
}
