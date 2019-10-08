package com.app.sistconApp.repository;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Ocorrencia;
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
public interface OcorrenciaRepository extends PagingAndSortingRepository<Ocorrencia, Long> {

        Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdOcorrenciaNot(String sigla, Condominio condominio, Long idOcorrencia);

	Page<Ocorrencia> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);
        
        @Query("SELECT COUNT(o) FROM Ocorrencia o")
        Long numeroOcorrencias();

}
