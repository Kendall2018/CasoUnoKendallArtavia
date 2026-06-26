package com.mycompany.CasoUnoKendallArtavia.Repository;

import com.mycompany.CasoUnoKendallArtavia.Domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}