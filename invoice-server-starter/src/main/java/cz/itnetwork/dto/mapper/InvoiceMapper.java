package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceEntity toEntity(InvoiceDTO source);

    InvoiceDTO toDTO(InvoiceEntity source);

    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    InvoiceEntity updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);
}
