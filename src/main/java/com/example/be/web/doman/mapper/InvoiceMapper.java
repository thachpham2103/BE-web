package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.payment.InvoiceResponseDto;
import com.example.be.web.doman.entity.Invoice;
import org.mapstruct.*;

/**
 * MapStruct mapper cho {@link Invoice}.
 *
 * @author auto-generated
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvoiceMapper {

    @Mapping(source = "payment.paymentId", target = "paymentId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    InvoiceResponseDto toResponse(Invoice entity);
}
