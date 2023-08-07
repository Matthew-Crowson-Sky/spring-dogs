package com.sky.springdogs.repos;

import com.sky.springdogs.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepo extends JpaRepository<Home, Integer> {

}
