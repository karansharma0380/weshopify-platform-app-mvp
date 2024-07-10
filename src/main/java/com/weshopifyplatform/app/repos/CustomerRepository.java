package com.weshopifyplatform.app.repos;

import org.springframework.data.repository.CrudRepository;

import com.weshopifyplatform.app.models.Customer;

// CrudRepository<T,ID> this interface will which is implemented by hibernate already for us 
// T-> Table Name
// ID -> datatype of ID column(Primary Key)
//We are using any Annotation for creating beans here, but it will created for sure.

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
