/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Informativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jadna Cavalcante
 */
@Repository
public interface InformativoRepository extends PagingAndSortingRepository<Informativo, Long> {
    
        Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdInformativoNot(String sigla, Condominio condominio, Long idInformativo);

	Page<Informativo> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
        
        @Query("SELECT COUNT(i) FROM Informativo i")
        Long numeroInformativos();
}
