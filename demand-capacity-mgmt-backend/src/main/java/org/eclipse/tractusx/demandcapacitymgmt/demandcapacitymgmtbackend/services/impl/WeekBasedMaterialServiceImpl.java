package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedMaterialDemandRequestDto;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedMaterialDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekDemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.WeekBasedMaterialDemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedMaterialService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeekBasedMaterialServiceImpl implements WeekBasedMaterialService {

    private final WeekBasedMaterialDemandRepository weekBasedMaterialDemandRepository;

    @Override
    public void createWeekBasedMaterial(WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto) {
        validateFields(weekBasedMaterialDemandRequestDto);
        WeekBasedMaterialDemandEntity weekBasedMaterialDemand = convertEntity(weekBasedMaterialDemandRequestDto);
        weekBasedMaterialDemandRepository.save(weekBasedMaterialDemand);

        List<WeekBasedMaterialDemandEntity> weekBasedMaterialDemandEntities = weekBasedMaterialDemandRepository.findAll();

    }

    private void validateFields(WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto){

        if(!UUIDUtil.checkValidUUID(weekBasedMaterialDemandRequestDto.getMaterialDemandId())){
            throw new BadRequestException("not a valid date");

        }

        weekBasedMaterialDemandRequestDto.getDemandSeries().forEach(demandWeekSeriesDto -> {

            demandWeekSeriesDto.getDemands().forEach(demandSeriesDto -> {

                if(!DataConverterUtil.itsMonday(demandSeriesDto.getCalendarWeek())){
                    throw new BadRequestException("not a valid date");
                }

            });
        });
    }

    private WeekBasedMaterialDemandEntity convertEntity(
        WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto
    ) {
        List<WeekDemandSeries> demandSeries = new LinkedList<>();
        weekBasedMaterialDemandRequestDto
            .getDemandSeries()
            .forEach(
                demandWeekSeriesDto -> {
                    WeekDemandSeries weekDemandSeries = WeekDemandSeries
                        .builder()
                        .expectedSupplierLocation(demandWeekSeriesDto.getExpectedSupplierLocation())
                        .build();
                    demandSeries.add(weekDemandSeries);
                }
            );

        WeekBasedMaterialDemand weekBasedMaterialDemand = WeekBasedMaterialDemand
            .builder()
            .unityOfMeasure(weekBasedMaterialDemandRequestDto.getUnityOfMeasure())
            .materialDescriptionCustomer(weekBasedMaterialDemandRequestDto.getMaterialDescriptionCustomer())
            .demandSeries(demandSeries)
            .build();

        return WeekBasedMaterialDemandEntity.builder().weekBasedMaterialDemand(weekBasedMaterialDemand).build();
    }
}
