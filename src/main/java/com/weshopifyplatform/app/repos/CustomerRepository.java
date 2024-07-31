package com.weshopifyplatform.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.weshopifyplatform.app.models.Customer;

// CrudRepository<T,ID> this interface will which is implemented by hibernate already for us 
// T-> Table Name
// ID -> datatype of ID column(Primary Key)
//We are using any Annotation for creating beans here, but it will created for sure.

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
	
	@Query("from Customer c where c.userName=:userName")
	//Query(nativeQuery=true, value="select * from customer c where c.customer_login=:userName") //SQL query
	public Customer findCustomerByUserName(@Param("userName") String userName);
	
	//@Query(nativeQuery=true,value="select * from customer c where c.mobile like %1%")
	@Query("from Customer c where c.mobile like %?1%")
	public List<Customer> searchCustomersByMobile(@Param("mobileNumLike") String mobileNumPattern);

}

