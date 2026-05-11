package com.az.nida.platform.payment.mapper;

import com.az.nida.platform.payment.dto.PaymentDto;
import com.az.nida.platform.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    Payment toEntity(PaymentDto dto);

    PaymentDto toResponse(Payment entity);
}