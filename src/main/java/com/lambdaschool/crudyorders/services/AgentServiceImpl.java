package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value = "agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    AgentRepository agentrepos;

    @Override
    public Agent findHiddenAgent(long doubleo) {
        return agentrepos.findById(doubleo).orElseThrow(() -> new EntityNotFoundException("He got away again!"));
    }

    @Transactional
    @Override
    public Agent save(Agent agent) {
        return agentrepos.save(agent);
    }
}
