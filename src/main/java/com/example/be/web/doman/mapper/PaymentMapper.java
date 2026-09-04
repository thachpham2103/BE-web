package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.payment.PaymentResponseDto;
import com.example.be.web.doman.entity.Payment;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy =
                NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(
            source = "classRegistration.registrationId",
            target = "registrationId"
    )
    @Mapping(
            source = "classRegistration.classEntity.classId",
            target = "classId"
    )
    @Mapping(
            source = "classRegistration.classEntity.title",
            target = "className"
    )
    @Mapping(source = "confirmedBy.id", target = "confirmedById")
    @Mapping(
            source = "confirmedBy.fullName",
            target = "confirmedByName"
    )
    @Mapping(source = "createDate", target = "createdAt")
    PaymentResponseDto toResponse(Payment entity);
}