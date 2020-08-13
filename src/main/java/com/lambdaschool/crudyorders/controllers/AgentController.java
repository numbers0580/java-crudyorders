package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //DELETE - localhost:5280/agents/unassigned/:agentcode
    @DeleteMapping(value = "/unassigned/{rogueagentid}")
    public ResponseEntity<?> terminateAgent(@PathVariable long rogueagentid) {
        agentService.burnNotice(rogueagentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE - localhost:5280/agents/unassigned/:agentcode(not found)
}
