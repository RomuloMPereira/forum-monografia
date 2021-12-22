package com.monografia.forum.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.monografia.forum.controllers.SubcategoriaController;
import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.entities.Subcategoria;

@Component
public class SubcategoriaModelAssembler extends RepresentationModelAssemblerSupport<Subcategoria, SubcategoriaDto>{

	public SubcategoriaModelAssembler() {
		super(SubcategoriaController.class, SubcategoriaDto.class);
	}

	@Override
	public SubcategoriaDto toModel(Subcategoria entity) {
		SubcategoriaDto model = instantiateModel(entity);
		model.add(linkTo(
				methodOn(SubcategoriaController.class)
				.buscarPorId(entity.getId(), entity.getCategoria().getId()))
				.withSelfRel());
		model.setId(entity.getId());
		model.setNome(entity.getNome());
		CategoriaDto categoriaDto = new CategoriaDto(entity.getCategoria());
		model.setCategoria(categoriaDto);
		return model;
	}

	@Override
	public CollectionModel<SubcategoriaDto> toCollectionModel(Iterable<? extends Subcategoria> entities) 
    {
        CollectionModel<SubcategoriaDto> subcategoriaModels = super.toCollectionModel(entities);
         
        subcategoriaModels.add(linkTo(methodOn(SubcategoriaController.class)
        		.listar(null, null, null, null, null))
        		.withSelfRel());
         
        return subcategoriaModels;
    }
}
