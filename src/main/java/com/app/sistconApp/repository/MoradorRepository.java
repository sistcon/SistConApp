/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Morador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcelo Fernandes
 */
@Repository
public interface MoradorRepository extends PagingAndSortingRepository<Morador, Long> {
    
    // Ordena por nome
    Page<Morador> findAllByCondominioOrderByNome(Condominio condominio, Pageable pagina);
    
    Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);
    
    Page<Morador> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
    Boolean existsBySiglaAndCondominioAndIdMoradorNot(String sigla, Condominio condominio, Long idMorador);
}
