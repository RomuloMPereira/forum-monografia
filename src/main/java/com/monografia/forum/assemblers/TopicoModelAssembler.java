package com.monografia.forum.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.monografia.forum.controllers.TopicoController;
import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.dto.RespostaDto;
import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.dto.UsuarioPayloadDto;
import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;

@Component
public class TopicoModelAssembler extends RepresentationModelAssemblerSupport<Topico, TopicoDto>{

	public TopicoModelAssembler() {
		super(TopicoController.class, TopicoDto.class);
	}

	@Override
	public TopicoDto toModel(Topico entity) {
		TopicoDto model = instantiateModel(entity);
		model.add(linkTo(
				methodOn(TopicoController.class)
				.buscarPorId(entity.getId()))
				.withSelfRel());
		model.setId(entity.getId());
		model.setTitulo(entity.getTitulo());
		model.setCorpo(entity.getCorpo());
		model.setInstante(entity.getInstante());
		UsuarioPayloadDto autorDto = new UsuarioPayloadDto(entity.getAutor());
		model.setAutor(autorDto);
		CategoriaDto categoriaDto = new CategoriaDto(entity.getCategoria());
		model.setCategoria(categoriaDto);
		SubcategoriaDto subcategoriaDto = new SubcategoriaDto(entity.getSubcategoria());
		model.setSubcategoria(subcategoriaDto);
		for(Usuario usuario : entity.getCurtidas()) {
			UsuarioPayloadDto dto = new UsuarioPayloadDto(usuario);
			model.getCurtidas().add(dto);
		}
		for(Resposta resposta : entity.getRespostas()) {
			RespostaDto dto = new RespostaDto(resposta);
			model.getRespostas().add(dto);
		}
		return model;
	}

	@Override
	public CollectionModel<TopicoDto> toCollectionModel(Iterable<? extends Topico> entities) 
    {
        CollectionModel<TopicoDto> topicoModels = super.toCollectionModel(entities);
         
        topicoModels.add(linkTo(methodOn(TopicoController.class)
        		.listar(null, null, null, null, null, null, null))
        		.withSelfRel());
         
        return topicoModels;
    }
}
