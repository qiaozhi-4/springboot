package com.zking.repository;

import com.zking.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
    User findUserById(int id);
    List<User> findUsersByUsernameLikeOrderByMoney(String name);

    List<User> queryByUsernameInAndMoneyLessThanEqualOrderByIdDesc(String[] username, Double money, Sort sort);
    List<User> queryByUsernameInAndMoneyLessThanEqualOrderByIdDesc(String[] username, Double money);

    List<User> findUsersByMoneyIsLessThan(Double money, Pageable pageable);
    Page<User> findUsersByMoneyIsGreaterThan(Double money, Pageable pageable);
}
