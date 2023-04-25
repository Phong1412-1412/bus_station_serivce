package com.busstation.repositories;

import com.busstation.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    boolean existsByusername(String username);
    Account findByusername(String username);
    Account findByUsernameAndPassword(String username, String password);
    //--------------------------------------------------------------------
    @Query("SELECT a FROM Account a JOIN User u ON a.accountId = u.account.accountId WHERE u.email = :email")
    Account findAccountByUserEmail(@Param("email") String email);
}
