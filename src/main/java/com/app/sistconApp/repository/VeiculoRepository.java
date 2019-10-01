/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;

import com.app.sistconApp.modelo.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jadna Cavalcante
 */
@Repository
public interface VeiculoRepository extends PagingAndSortingRepository<Veiculo, Long>{
    
    Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

    Boolean existsBySiglaAndCondominioAndIdVeiculoNot(String sigla, Condominio condominio, Long idVeiculo);

    Page<Veiculo> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
    
    
}

