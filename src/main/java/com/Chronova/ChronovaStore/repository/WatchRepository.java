package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch,Integer>, JpaSpecificationExecutor<Watch> {



}
