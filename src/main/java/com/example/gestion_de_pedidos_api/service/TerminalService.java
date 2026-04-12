package com.example.gestion_de_pedidos_api.service;

import com.example.gestion_de_pedidos_api.dto.CrearTerminalDto;
import com.example.gestion_de_pedidos_api.dto.TerminalDto;
import com.example.gestion_de_pedidos_api.exception.ResourceNotFoundException;
import com.example.gestion_de_pedidos_api.model.Terminal;
import com.example.gestion_de_pedidos_api.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    //Método para listar todas las terminales
    public List<TerminalDto> listarTerminales() {
        return terminalRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    //Método para buscar una terminal mediante su Id
    public TerminalDto buscarTerminalPorId(Long id) {
        Terminal terminal = terminalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La terminal con id: " + id + " no existe"));
        return toDto(terminal);
    }

    //Método para guardar una nueva terminal
    //Recibe un dto y devuelve otro de salida
    public TerminalDto guardaTerminal(CrearTerminalDto nuevaTerminalDto) {
        Terminal terminal = new Terminal();
        terminal.setNombre(nuevaTerminalDto.getNombre());
        Terminal guardada = terminalRepository.save(terminal);
        return toDto(guardada);
    }


    // *** MÉTODO DE MAPEO ***
    private TerminalDto toDto(Terminal terminal) {
        TerminalDto dto = new TerminalDto();
        dto.setId(terminal.getId());
        dto.setNombre(terminal.getNombre());
        return dto;
    }

}
