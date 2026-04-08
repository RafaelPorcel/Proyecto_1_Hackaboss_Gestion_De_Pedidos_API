package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.model.Terminal;
import com.example.gestion_de_pedidos_api.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminal")
public class TerminalController {
    @Autowired
    private TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<Terminal>> listarTerminales() {
        return ResponseEntity.ok(terminalService.listarTerminales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terminal> terminalPorId(@PathVariable Long id) {
        return ResponseEntity.ok(terminalService.buscarTerminalPorId(id));
    }

    @PostMapping
    public ResponseEntity<Terminal> crearTerminal(@RequestBody  Terminal nuevaTerminal) {
        return ResponseEntity.ok(terminalService.guardaTerminal(nuevaTerminal));
    }
}
