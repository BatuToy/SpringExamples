package com.dev.batu.strategy.repo;

/*
 * @created 26/05/2025 ~~ 21:33
 * author: batu
 */

import com.dev.batu.strategy.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = """
            SELECT * as owner 
            FROM t_account AS a 
            WHERE a.account_number = :account_number
            """,
            nativeQuery = true)
    Optional<Account> findByAccountNumber(@Param("account_number") String accountNumber);
}
