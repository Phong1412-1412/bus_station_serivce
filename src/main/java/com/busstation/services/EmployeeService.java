package com.busstation.services;

import org.springframework.data.domain.Page;

import com.busstation.dto.AccountDto;
import com.busstation.payload.request.EmployeeRequest;
import com.busstation.payload.response.ApiResponse;
import com.busstation.payload.response.DriverResponse;

public interface EmployeeService {
    Page<AccountDto> getAlLEmployee(String keyword, int pageNumber, int pageSize);

    Page<AccountDto> getAlLDriver(String keyword, int pageNumber, int pageSize);

    ApiResponse edit(String id, EmployeeRequest employeeRequest);

    DriverResponse getAllCalendarByDriver();

    Page<DriverResponse> getAllTripAlreadyCar(int pageNo, int pageSize);

}
