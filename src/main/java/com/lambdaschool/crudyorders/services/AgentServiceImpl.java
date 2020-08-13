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

    @Transactional
    @Override
    public void burnNotice(long rogueid) {
        //We are not going to simply delete an Agent with an existing id like the Customers and Orders
        //Instead, we'll check if the agent has any customers. If not, then we'll delete

        Agent checkAgent = agentrepos.findById(rogueid)
                .orElseThrow(() -> new EntityNotFoundException("Agent ID: " + rogueid + " escaped!"));

        if(checkAgent.getCustomers().size() == 0) {
            agentrepos.deleteById(checkAgent.getAgentcode());
        } else {
            throw new EntityNotFoundException("Found customers for Agent # " + checkAgent.getAgentcode());
        }
    }
}
