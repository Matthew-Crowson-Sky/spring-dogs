package com.sky.springdogs.repos;

import com.sky.springdogs.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// The generics here specify the data type (Dog), and the type for it's ID field (Integer)
// The ID could also be String, which is why the ID type needs to be specified here.
@Repository
public interface DogRepo extends JpaRepository<Dog, Integer> {

    // Spring will intuit the implementation of this method automatically
    List<Dog> findByNameContainsIgnoreCase(String name);

    // JPQL -> Java Persistence Query Language
    // Where implementation of a method can not be intuited, use JPL or SQL to specify query
    // @Query("SELECT d.age FROM Dog WHERE d.name = ?1") // JPQL version
    @Query(value =  "SELECT age FROM dog WHERE LOWER(name) = LOWER(?1)", nativeQuery = true) // SQL Version
    List<Integer> findAgeByName(String name);

}
