package com.az.payment.controller;

import com.az.common.response.ApiResponse;
import com.az.payment.dto.BalanceResponse;
import com.az.payment.dto.PaymentDto;
import com.az.payment.dto.TopUpRequest;
import com.az.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    @GetMapping("/balance/{parentId}")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                paymentService.getBalance(parentId)));
    }

    @Override
    @PostMapping("/topup/{parentId}")
    public ResponseEntity<ApiResponse<PaymentDto>> topUp(
            @PathVariable Long parentId,
            @RequestBody TopUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        paymentService.topUp(parentId, request),
                        "Balans uğurla artırıldı"));
    }

    @Override
    @PostMapping("/subscription/{parentId}")
    public ResponseEntity<ApiResponse<PaymentDto>> paySubscription(
            @PathVariable Long parentId,
            @RequestParam Long studentId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        paymentService.paySubscription(parentId, studentId),
                        "Abunəlik uğurla ödənildi"));
    }

    @Override
    @PostMapping("/exam/{parentId}")
    public ResponseEntity<ApiResponse<PaymentDto>> payExamFee(
            @PathVariable Long parentId,
            @RequestParam Long studentId,
            @RequestParam Long examId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        paymentService.payExamFee(parentId, studentId, examId),
                        "İmtahan haqqı uğurla ödənildi"));
    }

    @Override
    @GetMapping("/history/{parentId}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentHistory(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                paymentService.getPaymentHistory(parentId)));
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getStudentPayments(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                paymentService.getStudentPayments(studentId)));
    }
}