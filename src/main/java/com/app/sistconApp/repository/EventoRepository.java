/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcelo Fernandes
 */
@Repository
public interface EventoRepository extends PagingAndSortingRepository<Evento, Long> {
   
    Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

    Boolean existsBySiglaAndCondominioAndIdEventoNot(String sigla, Condominio condominio, Long idEvento);

    Page<Evento> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
    
    @Query("SELECT COUNT(e) FROM Evento e")
       Long numeroEventos();
}
