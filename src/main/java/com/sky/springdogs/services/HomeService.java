package com.sky.springdogs.services;

import com.sky.springdogs.domain.Home;
import com.sky.springdogs.exceptions.HomeNotFoundException;
import com.sky.springdogs.repos.HomeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private HomeRepo repo;

    public HomeService(HomeRepo repo) {
        this.repo = repo;
    }

    public Home create(Home home) { return this.repo.save(home); }

    public List<Home> create(List<Home> newHomes) {
        return this.repo.saveAll(newHomes);
    }


    public List<Home> getAll() {
        return this.repo.findAll();
    }

    public Home get(Integer id) {
        return this.repo.findById(id).orElseThrow(HomeNotFoundException::new);
    }

    public Home update(Integer id, String address, Boolean hasGarden) {
        Home toUpdate = this.get(id);

        if (address != null) toUpdate.setAddress(address);
        if (hasGarden != null) toUpdate.setHasGarden(hasGarden);

        return this.repo.save(toUpdate);
    }

    public Home remove(Integer id) {
        Home toDelete = this.get(id);
        this.repo.deleteById(id);
        return toDelete;
    }
}
