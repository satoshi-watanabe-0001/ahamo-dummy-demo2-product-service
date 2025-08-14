package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.DataPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPlanRepository extends JpaRepository<DataPlan, String> {
}
