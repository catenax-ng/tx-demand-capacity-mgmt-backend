package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters;

import com.google.gson.Gson;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class WeekBasedMaterialConverter implements AttributeConverter<WeekBasedMaterialDemand, String> {

    private final static Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(WeekBasedMaterialDemand mjo) {
        return GSON.toJson(mjo);
    }

    @Override
    public WeekBasedMaterialDemand convertToEntityAttribute(String dbData) {
        return GSON.fromJson(dbData, WeekBasedMaterialDemand.class);
    }

}
