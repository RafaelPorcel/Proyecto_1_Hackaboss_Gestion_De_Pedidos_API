package com.example.gestion_de_pedidos_api.service;

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
    public List<Terminal> listarTerminales() {
        return terminalRepository.findAll();
    }

    //Método para buscar una terminal mediante su Id
    public Terminal buscarTerminalPorId(Long id) {
        return terminalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La terminal con id: " + id + " no existe"));
    }

    //Método para guardar una nueva terminal
    public Terminal guardaTerminal(Terminal nuevaTerminal) {
        return terminalRepository.save(nuevaTerminal);
    }


}
