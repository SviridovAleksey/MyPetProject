package com.sviridov.resource.persistence.repository.users;

import com.sviridov.resource.persistence.model.users.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByName(String name);

}
