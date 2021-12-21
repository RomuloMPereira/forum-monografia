package com.monografia.forum.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.monografia.forum.controllers.CategoriaController;
import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;

@Component
public class CategoriaModelAssembler extends RepresentationModelAssemblerSupport<Categoria, CategoriaDto>{

	public CategoriaModelAssembler() {
		super(CategoriaController.class, CategoriaDto.class);
	}

	@Override
	public CategoriaDto toModel(Categoria entity) {
		CategoriaDto model = instantiateModel(entity);
		model.add(linkTo(
				methodOn(CategoriaController.class)
				.buscarPorId(entity.getId()))
				.withSelfRel());
		model.setId(entity.getId());
		model.setNome(entity.getNome());
		for(Topico topico : entity.getTopicos()) {
			TopicoDto dto = new TopicoDto(topico);
			model.getTopicos().add(dto);
		}
		for(Subcategoria subcategoria : entity.getSubcategorias()) {
			SubcategoriaDto dto = new SubcategoriaDto(subcategoria);
			model.getSubcategorias().add(dto);
		}
		return model;
	}

	@Override
	public CollectionModel<CategoriaDto> toCollectionModel(Iterable<? extends Categoria> entities) 
    {
        CollectionModel<CategoriaDto> categoriaModels = super.toCollectionModel(entities);
         
        categoriaModels.add(linkTo(methodOn(CategoriaController.class)
        		.listar(null, null, null, null))
        		.withSelfRel());
         
        return categoriaModels;
    }
}
