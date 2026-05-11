package com.az.nida.platform.payment.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.payment.dto.BalanceResponse;
import com.az.nida.platform.payment.dto.PaymentDto;
import com.az.nida.platform.payment.dto.TopUpRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentController {

    ResponseEntity<ApiResponse<BalanceResponse>> getBalance(Long parentId);

    ResponseEntity<ApiResponse<PaymentDto>> topUp(Long parentId, @Valid TopUpRequest request);

    ResponseEntity<ApiResponse<PaymentDto>> paySubscription(Long parentId, Long studentId);

    ResponseEntity<ApiResponse<PaymentDto>> payExamFee(Long parentId, Long studentId, Long examId);

    ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentHistory(Long parentId);

    ResponseEntity<ApiResponse<List<PaymentDto>>> getStudentPayments(Long studentId);
}