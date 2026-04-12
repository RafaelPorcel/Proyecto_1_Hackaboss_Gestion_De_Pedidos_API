package com.example.gestion_de_pedidos_api.service;

import com.example.gestion_de_pedidos_api.dto.CategoriaDto;
import com.example.gestion_de_pedidos_api.dto.CrearCategoriaDto;
import com.example.gestion_de_pedidos_api.exception.IllegalStateException;
import com.example.gestion_de_pedidos_api.exception.ResourceNotFoundException;
import com.example.gestion_de_pedidos_api.model.Categoria;
import com.example.gestion_de_pedidos_api.model.Producto;
import com.example.gestion_de_pedidos_api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

//    Listar categorias
    public List<CategoriaDto> listarCategorias(){
        return categoriaRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }
//    Crear categorias
    public CategoriaDto guardarCategoria(CrearCategoriaDto crearCategoriaDto){
        Optional<Categoria> categoriaExistente=categoriaRepository.findByNombre(crearCategoriaDto.getNombre());
        if(categoriaExistente.isPresent()){
            throw new IllegalStateException("La categoria ya existe");
        }

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(crearCategoriaDto.getNombre());
        Categoria categoriaGuardada = categoriaRepository.save(nuevaCategoria);
        return toDto(categoriaGuardada);//Este dto devolvemos
    }
//    Obtener categorias por id
    public CategoriaDto obtenerPorId(Long id){
        return toDto(categoriaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada")));
    }

    // *** MÉTODOS DE MAPEO ***

    private CategoriaDto toDto (Categoria categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
}
