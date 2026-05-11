package com.az.nida.platform.payment.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.payment.dto.BalanceResponse;
import com.az.nida.platform.payment.dto.PaymentDto;
import com.az.nida.platform.payment.dto.TopUpRequest;
import com.az.nida.platform.payment.entity.Payment;
import com.az.nida.platform.payment.entity.PaymentStatus;
import com.az.nida.platform.payment.entity.PaymentType;
import com.az.nida.platform.payment.mapper.PaymentMapper;
import com.az.nida.platform.payment.repository.PaymentRepository;
import com.az.nida.platform.user.entity.Parent;
import com.az.nida.platform.user.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal SUBSCRIPTION_FEE = BigDecimal.valueOf(29.99);
    private static final BigDecimal EXAM_FEE         = BigDecimal.valueOf(9.99);

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ParentRepository parentRepository;

    @Override
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long parentId) {
        Parent parent = getParent(parentId);
        return new BalanceResponse(parentId, parent.getBalance());
    }

    @Override
    @Transactional
    public PaymentDto topUp(Long parentId, TopUpRequest request) {
        Parent parent = getParent(parentId);

        parent.setBalance(parent.getBalance().add(request.amount()));
        parentRepository.save(parent);

        Payment payment = Payment.builder()
                .parentId(parentId)
                .amount(request.amount())
                .type(PaymentType.TOP_UP)
                .status(PaymentStatus.COMPLETED)
                .description("Balans artırıldı: " + request.amount() + " AZN")
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Balans artırıldı: parentId={}, amount={}", parentId, request.amount());
        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PaymentDto paySubscription(Long parentId, Long studentId) {
        Parent parent = getParent(parentId);

        if (parent.getBalance().compareTo(SUBSCRIPTION_FEE) < 0) {
            throw BusinessException.badRequest("Balans kifayət deyil. Lazım olan məbləğ: " + SUBSCRIPTION_FEE + " AZN");
        }

        parent.setBalance(parent.getBalance().subtract(SUBSCRIPTION_FEE));
        parentRepository.save(parent);

        Payment payment = Payment.builder()
                .parentId(parentId)
                .studentId(studentId)
                .amount(SUBSCRIPTION_FEE)
                .type(PaymentType.SUBSCRIPTION)
                .status(PaymentStatus.COMPLETED)
                .description("Aylıq abunəlik ödənişi")
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Abunəlik ödənildi: parentId={}, studentId={}", parentId, studentId);
        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PaymentDto payExamFee(Long parentId, Long studentId, Long examId) {
        Parent parent = getParent(parentId);

        if (parent.getBalance().compareTo(EXAM_FEE) < 0) {
            throw BusinessException.badRequest("Balans kifayət deyil. Lazım olan məbləğ: " + EXAM_FEE + " AZN");
        }

        parent.setBalance(parent.getBalance().subtract(EXAM_FEE));
        parentRepository.save(parent);

        Payment payment = Payment.builder()
                .parentId(parentId)
                .studentId(studentId)
                .amount(EXAM_FEE)
                .type(PaymentType.EXAM_FEE)
                .status(PaymentStatus.COMPLETED)
                .description("Sinaq imtahanı ödənişi: examId=" + examId)
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("İmtahan haqqı ödənildi: parentId={}, studentId={}, examId={}", parentId, studentId, examId);
        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentHistory(Long parentId) {
        return paymentRepository.findByParentIdOrderByCreatedAtDesc(parentId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getStudentPayments(Long studentId) {
        return paymentRepository.findByStudentIdOrderByCreatedAtDesc(studentId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }


    private Parent getParent(Long parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> BusinessException.notFound("Valideyn tapılmadı"));
    }
}