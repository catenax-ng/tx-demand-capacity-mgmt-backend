package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters;

import com.google.gson.Gson;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;

@Converter(autoApply = true)
public class WeekBasedMaterialConverter implements AttributeConverter<WeekBasedMaterialDemand, String> {

    private static final Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(WeekBasedMaterialDemand mjo) {
        return GSON.toJson(mjo);
    }

    @Override
    public WeekBasedMaterialDemand convertToEntityAttribute(String dbData) {
        return GSON.fromJson(dbData, WeekBasedMaterialDemand.class);
    }
}
