package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

public interface AgentService {
    Agent findHiddenAgent(long doubleo);

    Agent save(Agent agent);
}
