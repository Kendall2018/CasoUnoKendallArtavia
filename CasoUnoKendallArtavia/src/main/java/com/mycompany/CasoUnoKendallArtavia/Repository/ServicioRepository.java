package com.mycompany.CasoUnoKendallArtavia.Repository;

import com.mycompany.CasoUnoKendallArtavia.Domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

}