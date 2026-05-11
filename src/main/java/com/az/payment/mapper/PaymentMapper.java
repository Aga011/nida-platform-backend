package com.az.payment.mapper;

import com.az.payment.dto.PaymentDto;
import com.az.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    Payment toEntity(PaymentDto dto);

    PaymentDto toResponse(Payment entity);
}