package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Long> {
}
