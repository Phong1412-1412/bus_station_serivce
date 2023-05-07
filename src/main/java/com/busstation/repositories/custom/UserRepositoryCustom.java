package com.busstation.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busstation.entities.Account;

public interface UserRepositoryCustom {
    Page<Account> getFilter(String keyword, String role, Pageable pageable);
}
