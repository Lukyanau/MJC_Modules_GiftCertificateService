package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserWithoutOrdersDto;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if (user.getOrders() != null) {
            userDto.setOrders(user.getOrders().stream().
                    map(orderMapper::convertToDto).collect(Collectors.toList()));
        }
        return userDto;
    }

    public UserWithoutOrdersDto convertToDtoWithoutOrders(User user) {
        return modelMapper.map(user, UserWithoutOrdersDto.class);
    }
}
