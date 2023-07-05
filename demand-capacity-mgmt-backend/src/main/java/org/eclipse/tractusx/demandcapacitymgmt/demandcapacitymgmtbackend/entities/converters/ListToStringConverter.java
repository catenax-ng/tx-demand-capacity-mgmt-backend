package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters;

import java.util.List;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attributesParam) {
        return Optional.ofNullable(attributesParam).map(attributes -> String.join(",", attributes)).orElse(null);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbDataParam) {
        return Optional.ofNullable(dbDataParam).map(dbData -> List.of(dbData.split(","))).orElse(List.of());
    }
}
