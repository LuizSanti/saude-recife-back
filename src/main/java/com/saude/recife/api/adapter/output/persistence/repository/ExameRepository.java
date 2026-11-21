package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.ExameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExameRepository extends JpaRepository<ExameEntity, Long> {

    List<ExameEntity> findByAtivoTrueOrderByNomeAsc();
}
