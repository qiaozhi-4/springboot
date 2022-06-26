package com.zking.repository;

import com.zking.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,Integer> {
    List<Employee> findByNameLike(String name);

}
