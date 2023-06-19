package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters;

import com.google.gson.Gson;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedCapacityGroup;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class WeekBasedCapacityGroupConverter implements AttributeConverter<WeekBasedCapacityGroup, String> {

    private final static Gson GSON = new Gson();
    @Override
    public String convertToDatabaseColumn(WeekBasedCapacityGroup attribute) {
        return GSON.toJson(attribute);

    }

    @Override
    public WeekBasedCapacityGroup convertToEntityAttribute(String dbData) {
        return GSON.fromJson(dbData, WeekBasedCapacityGroup.class);
    }
}
