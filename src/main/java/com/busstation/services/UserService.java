package com.busstation.services;

import org.springframework.data.domain.Page;

import com.busstation.dto.AccountDto;
import com.busstation.payload.request.UserRequest;
import com.busstation.payload.response.ApiResponse;

public interface UserService {

    Page<AccountDto> getAlL(String keyword, int pageNumber, int pageSize);

    ApiResponse edit(String id, UserRequest userRequest);
    
    ApiResponse setStatus(String id);
}
