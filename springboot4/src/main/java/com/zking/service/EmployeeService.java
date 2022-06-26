package com.zking.service;

import com.alibaba.fastjson2.JSON;
import com.zking.entity.Employee;
import com.zking.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    //插入员工
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    //分页
    public List<Employee> pageAll(int pageNum, int pageSize) {
        //绑定这个key
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("page::" + pageNum);
        //先查询redis缓存有没有
        String str = (String) ops.get();
        if (str == null) {
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("salary"));
            Page<Employee> page = employeeRepository.findAll(pageable);
            //查询之后转字符串，存入redis
            String jsonString = JSON.toJSONString(page.toList());
            ops.set(jsonString);
            return page.toList();
        }
        return JSON.parseArray(str, Employee.class);
    }

    //删除
    public void delete(int id) {
        //清除相关缓存
        Set<String> keys = redisTemplate.keys("page::" + "*");
        redisTemplate.delete(keys);
        employeeRepository.deleteById(id);
    }

    //修改
    public Employee update(Employee employee) {
        //清除相关缓存
        //分页缓存
        Set<String> keys = redisTemplate.keys("page::" + "*");
        //根据id查询缓存
        keys.add("id::" + employee.getId());
        //模糊缓存
        Set<String> keys1 = redisTemplate.keys("like::" + "*");
        keys.addAll(keys1);
        redisTemplate.delete(keys);
        return employeeRepository.save(employee);
    }

    //根据id查询
    public Employee findById(int id) {
        //绑定这个key
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("id::" + id);
        //先查询redis缓存有没有
        String str = (String) ops.get();
        if (str == null) {
             Employee employee = employeeRepository.findById(id).get();
            //查询之后转字符串，存入redis
            String jsonString = JSON.toJSONString(employee);
            ops.set(jsonString);
            return employee;
        }
        return JSON.parseObject(str, Employee.class);
    }

    //根据名字模糊查询
    public  List<Employee> findByNameLike(String name) {
        //绑定这个key
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("like::" + name);
        //先查询redis缓存有没有
        String str = (String) ops.get();
        if (str == null) {
            List<Employee> employees = employeeRepository.findByNameLike(name);
            //查询之后转字符串，存入redis
            String jsonString = JSON.toJSONString(employees);
            ops.set(jsonString);
            return employees;
        }
        return JSON.parseArray(str, Employee.class);
    }

}
