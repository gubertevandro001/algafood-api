package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput input) {
        return modelMapper.map(input, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput input, Grupo grupo) {
        modelMapper.map(input, grupo);
    }
}
