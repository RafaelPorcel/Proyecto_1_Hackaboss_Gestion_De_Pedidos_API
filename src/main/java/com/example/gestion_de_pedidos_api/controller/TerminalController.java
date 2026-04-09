package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.dto.CrearTerminalDto;
import com.example.gestion_de_pedidos_api.model.Terminal;
import com.example.gestion_de_pedidos_api.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminales")
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
    public ResponseEntity<Terminal> crearTerminal(@RequestBody CrearTerminalDto nuevaTerminalDto) {
        Terminal nuevaTerminal = new Terminal();
        nuevaTerminal.setNombre(nuevaTerminalDto.getNombre());
        return ResponseEntity.ok(terminalService.guardaTerminal(nuevaTerminal));
    }
}
