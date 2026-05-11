package com.az.nida.platform.payment.repository;

import com.az.nida.platform.payment.entity.Payment;
import com.az.nida.platform.payment.entity.PaymentStatus;
import com.az.nida.platform.payment.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByParentIdOrderByCreatedAtDesc(Long parentId);

    List<Payment> findByStudentIdOrderByCreatedAtDesc(Long studentId);

    List<Payment> findByParentIdAndStatus(Long parentId, PaymentStatus status);

    List<Payment> findByParentIdAndType(Long parentId, PaymentType type);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.parentId = :parentId " +
            "AND p.type = 'TOP_UP' AND p.status = 'COMPLETED'")
    BigDecimal sumTopUpByParentId(Long parentId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.parentId = :parentId " +
            "AND p.type != 'TOP_UP' AND p.status = 'COMPLETED'")
    BigDecimal sumSpentByParentId(Long parentId);
}