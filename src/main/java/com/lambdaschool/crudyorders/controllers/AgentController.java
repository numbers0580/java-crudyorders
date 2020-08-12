package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    //localhost:5280/agents/agent/:agentcode
    @GetMapping(value = "/agent/{doubleo}", produces = "application/json")
    public ResponseEntity<?> listAgent(@PathVariable long doubleo) {
        Agent bond = agentService.findHiddenAgent(doubleo);
        return new ResponseEntity<>(bond, HttpStatus.OK);
    }
}
