package com.az.payment.controller;

import com.az.common.response.ApiResponse;
import com.az.payment.dto.BalanceResponse;
import com.az.payment.dto.PaymentDto;
import com.az.payment.dto.TopUpRequest;
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