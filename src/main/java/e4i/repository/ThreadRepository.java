package e4i.repository;

import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.Thread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Thread entity.
 */
@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {

    @Query(value = "select distinct thread from Thread thread left join fetch thread.advertisements",
        countQuery = "select count(distinct thread) from Thread thread")
    Page<Thread> findAllWithEagerRelationships(Pageable pageable);
    
    @Query("SELECT thread FROM Thread thread " +
            "JOIN thread.messages message " +
            "WHERE (thread.companySender.id = :companyId " +
            "AND EXISTS (SELECT 1 FROM thread.messages m WHERE m = message and message.isDeletedSender = :isDeleted)) " +
            "OR (thread.companyReceiver.id = :companyId " +
            "AND EXISTS (SELECT 1 FROM thread.messages m WHERE m = message and message.isDeletedReceiver = :isDeleted)) " +
    		"GROUP BY thread " +
            "ORDER BY max(message.datetime) DESC")
    Page<Thread> findAllByCompany(@Param("companyId") Long companyId, @Param("isDeleted") Boolean isDeleted, Pageable pageable);
    
    @Query("SELECT thread FROM Thread thread " +
            "JOIN thread.messages message " +
            "WHERE thread.companySender.id = :companyId " +
            "AND EXISTS (SELECT 1 FROM thread.messages m WHERE m = message and message.isDeletedSender = :isDeletedSender) " +
    		"GROUP BY thread " +
            "ORDER BY max(message.datetime) DESC")
    Page<Thread> findAllByCompanySender(@Param("companyId") Long companyId, @Param("isDeletedSender") Boolean isDeletedSender, Pageable pageable);
    
    @Query("SELECT thread FROM Thread thread " +
            "JOIN thread.messages message " +
            "WHERE thread.companyReceiver.id = :companyId " +
            "AND EXISTS (SELECT 1 FROM thread.messages m WHERE m = message and m.isDeletedReceiver = :isDeletedReceiver) " +
    		"GROUP BY thread " +
            "ORDER BY max(message.datetime) DESC")
    Page<Thread> findAllByCompanyReceiver(@Param("companyId") Long companyId, @Param("isDeletedReceiver") Boolean isDeletedReceiver, Pageable pageable);

    @Query("select distinct thread from Thread thread left join fetch thread.advertisements")
    List<Thread> findAllWithEagerRelationships();

    @Query("select thread from Thread thread left join fetch thread.advertisements where thread.id =:id")
    Optional<Thread> findOneWithEagerRelationships(@Param("id") Long id);
    
    Set<Thread> findAllByCollaborations(Collaboration collaboration);
    
	@Query("SELECT t.companySender FROM Thread t WHERE t.id = :threadId")
    Optional<Company> findCompanySenderByThreadId(@Param("threadId") Long threadId);
	
	@Query("SELECT t.companyReceiver FROM Thread t WHERE t.id = :threadId")
    Optional<Company> findCompanyReceiverByThreadId(@Param("threadId") Long threadId);
}
