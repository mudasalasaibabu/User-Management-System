package com.example.usermanagement.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.Role;
import com.example.usermanagement.entity.User;

import jakarta.transaction.Transactional;
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//	boolean existsByEmailId(String emailId);
//	Optional<User> findByEmailId(String emailId);
//	
//	 long countByRole(Role role);
//	 long countByCreatedAtAfter(LocalDateTime date);
//	 long countByLastLoginAtAfter(LocalDateTime date);
//	 @Query(value = """
//			    SELECT MONTH(created_at) AS month, COUNT(*) AS total
//			    FROM users
//			    WHERE YEAR(created_at) = :year
//			    GROUP BY MONTH(created_at)
//			    ORDER BY MONTH(created_at)
//			  """,
//			  nativeQuery = true
//			)
//			List<Object[]> getMonthlyRegistrations(@Param("year") int year);
//			
//@Query(value = "SELECT DAYOFWEEK(last_login_at) AS day,COUNT(*) AS total FROM users WHERE last_login_at >= :startDate GROUP BY DAYOFWEEK(last_login_at) ORDER BY DAYOFWEEK(last_login_at)" , nativeQuery = true)
//List<Object[]> getWeeklyLogins(@Param("startDate") LocalDateTime startDate);
//
//@Query(value = "SELECT DAYOFWEEK(created_at) AS day, COUNT(*) AS total FROM users WHERE created_at >= :startDate GROUP BY DAYOFWEEK(created_at) ORDER BY DAYOFWEEK(created_at)", nativeQuery = true)
//List<Object[]> getWeeklySignups(@Param("startDate") LocalDateTime startDate);
//
//@Query( value = "SELECT * FROM users WHERE DATE(last_login_at) = CURDATE()", nativeQuery = true)
//	List<User> findActiveUsersToday(LocalDateTime startOfDay);
//
//@Transactional
//@Modifying
//@Query(value = "UPDATE users SET password = ?2 WHERE email_id = ?1",nativeQuery = true
//)
//void updatePassword(String email, String password);
//
//
//}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailId(String emailId);
    Optional<User> findByEmailId(String emailId);

    long countByRole(Role role);
    long countByCreatedAtAfter(LocalDateTime date);
    long countByLastLoginAtAfter(LocalDateTime date);

    //  Monthly registrations (PostgreSQL-safe + Integer output)
    @Query(value = """
        SELECT 
            CAST(EXTRACT(MONTH FROM created_at) AS INTEGER) AS month,
            COUNT(*) AS total
        FROM users
        WHERE EXTRACT(YEAR FROM created_at) = :year
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getMonthlyRegistrations(@Param("year") int year);

    //  Weekly logins (PostgreSQL-safe + Integer output)
    @Query(value = """
        SELECT 
            CAST(EXTRACT(DOW FROM last_login_at) AS INTEGER) AS day,
            COUNT(*) AS total
        FROM users
        WHERE last_login_at >= :startDate
        GROUP BY day
        ORDER BY day
    """, nativeQuery = true)
    List<Object[]> getWeeklyLogins(@Param("startDate") LocalDateTime startDate);

    //  Weekly signups (PostgreSQL-safe + Integer output)
    @Query(value = """
        SELECT 
            CAST(EXTRACT(DOW FROM created_at) AS INTEGER) AS day,
            COUNT(*) AS total
        FROM users
        WHERE created_at >= :startDate
        GROUP BY day
        ORDER BY day
    """, nativeQuery = true)
    List<Object[]> getWeeklySignups(@Param("startDate") LocalDateTime startDate);

    //  Active users today (keeps service unchanged)
    @Query(value = """
        SELECT *
        FROM users
        WHERE last_login_at >= :startOfDay
    """, nativeQuery = true)
    List<User> findActiveUsersToday(@Param("startOfDay") LocalDateTime startOfDay);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET password = ?2 WHERE email_id = ?1", nativeQuery = true)
    void updatePassword(String email, String password);
}
