package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.OverseaCallingOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverseaCallingOptionRepository extends JpaRepository<OverseaCallingOption, String> {
}
