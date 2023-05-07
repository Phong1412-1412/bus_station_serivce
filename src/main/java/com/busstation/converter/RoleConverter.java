package com.busstation.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.busstation.dto.RoleDto;
import com.busstation.entities.Role;

@Component
public class RoleConverter {
    @Autowired
    private ModelMapper modelMapper;

    public RoleDto converToDto(Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }
}
