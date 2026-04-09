package com.example.gestion_de_pedidos_api.service;

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
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }
//    Crear categorias
    public Categoria guardarCategoria(Categoria categoria){
        Optional<Categoria> categoriaExistente=categoriaRepository.findByNombre(categoria.getNombre());
        if(categoriaExistente.isPresent()){
            throw new IllegalStateException("La categoria ya existe");
        }
        return categoriaRepository.save(categoria);
    }
//    Obtener categorias por id
    public Categoria obtenerPorId(Long id){
        return categoriaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada"));
    }
}
