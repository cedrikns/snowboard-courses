package ru.tsedrik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tsedrik.domain.User;
import ru.tsedrik.domain.UserStatus;

import java.util.Optional;


/**
 * Интерфейс управления персистентным состоянием объектов типа User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByUserName(String userName);

    boolean existsByIdAndStatusIsNot(Long id, UserStatus status);

    @Modifying
    @Query("update User u set u.status = :status where u.id = :id")
    int updateUserSetStatusForId(@Param("status") UserStatus status, @Param("id") Long id);
}
