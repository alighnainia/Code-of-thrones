package com.example.microservicecatalogues.Repository;

import com.example.microservicecatalogues.Entity.Age;
import com.example.microservicecatalogues.Entity.Catalogues;
import com.example.microservicecatalogues.Entity.Type;
import com.example.microservicecatalogues.Entity.langue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CataloguesRepository extends JpaRepository<Catalogues,Long> {

}
