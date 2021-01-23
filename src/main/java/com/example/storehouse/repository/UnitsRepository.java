package com.example.storehouse.repository;


import com.example.storehouse.model.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UnitsRepository extends JpaRepository<Unit, Integer> {

    List<Unit> findByNameContaining(String name);

}
