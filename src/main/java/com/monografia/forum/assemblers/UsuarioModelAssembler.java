package com.monografia.forum.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.monografia.forum.controllers.UsuarioController;
import com.monografia.forum.dto.UsuarioPayloadDto;
import com.monografia.forum.entities.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioPayloadDto>{

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioPayloadDto.class);
	}

	@Override
	public UsuarioPayloadDto toModel(Usuario entity) {
		UsuarioPayloadDto model = instantiateModel(entity);
		model.add(linkTo(
				methodOn(UsuarioController.class)
				.buscarPorId(entity.getId()))
				.withSelfRel());
		model.setId(entity.getId());
		model.setNome(entity.getNome());
		model.setEmail(entity.getEmail());
		/*for(Funcao funcao : entity.getFuncoes()) {
			FuncaoDto funcaoDto = new FuncaoDto(funcao);
			model.getFuncoes().add(funcaoDto);
		}
		for(Topico topico : entity.getTopicos()) {
			TopicoDto dto = new TopicoDto(topico);
			model.getTopicos().add(dto);
		}
		for(Topico topico : entity.getTopicos()) {
			TopicoDto dto = new TopicoDto(topico);
			model.getTopicos().add(dto);
		}
		for(Topico topico : entity.getTopicosCurtidos()) {
			TopicoDto dto = new TopicoDto(topico);
			model.getTopicosCurtidos().add(dto);
		}
		for(Resposta resposta : entity.getRespostas()) {
			RespostaDto dto = new RespostaDto(resposta);
			model.getRespostas().add(dto);
		}*/
		return model;
	}

	@Override
	public CollectionModel<UsuarioPayloadDto> toCollectionModel(Iterable<? extends Usuario> entities) 
    {
        CollectionModel<UsuarioPayloadDto> usuarioModels = super.toCollectionModel(entities);
         
        usuarioModels.add(linkTo(methodOn(UsuarioController.class)
        		.listar(null, null, null, null, null))
        		.withSelfRel());
         
        return usuarioModels;
    }
}
