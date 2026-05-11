package com.az.nida.platform.payment.service;

import com.az.nida.platform.payment.dto.BalanceResponse;
import com.az.nida.platform.payment.dto.PaymentDto;
import com.az.nida.platform.payment.dto.TopUpRequest;

import java.util.List;

public interface PaymentService {

    BalanceResponse getBalance(Long parentId);

    PaymentDto topUp(Long parentId, TopUpRequest request);

    PaymentDto paySubscription(Long parentId, Long studentId);

    PaymentDto payExamFee(Long parentId, Long studentId, Long examId);

    List<PaymentDto> getPaymentHistory(Long parentId);

    List<PaymentDto> getStudentPayments(Long studentId);
}