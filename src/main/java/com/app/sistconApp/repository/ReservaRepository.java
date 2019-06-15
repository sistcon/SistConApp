/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Reserva;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jadna Cavalcante
 */
@Repository
public interface ReservaRepository extends PagingAndSortingRepository<Reserva, Long> {
    
    Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);
        
        
        //Boolean existsByCondominioAndDiaAndIdReservaNot(Condominio condominio,LocalDate dia, Long idReserva);
	Boolean existsBySiglaAndCondominioAndIdReservaNot(String sigla, Condominio condominio, Long idReserva);

	Page<Reserva> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
    
}
