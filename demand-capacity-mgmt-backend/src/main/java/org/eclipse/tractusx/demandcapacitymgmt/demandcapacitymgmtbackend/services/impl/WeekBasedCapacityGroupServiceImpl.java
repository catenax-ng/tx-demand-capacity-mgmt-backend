package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedCapacityGroupEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.Capacity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.DemandCategory;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.LikedDemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedCapacityGroup;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.WeekBasedCapacityGroupRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedCapacityGroupService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeekBasedCapacityGroupServiceImpl implements WeekBasedCapacityGroupService {

    private final WeekBasedCapacityGroupRepository weekBasedCapacityGroupRepository;

    @Override
    public void createWeekBasedCapacityGroup(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {
        validateFields(weekBasedCapacityGroupRequest);

        WeekBasedCapacityGroupEntity weekBasedCapacityGroup = convertEntity(weekBasedCapacityGroupRequest);
        weekBasedCapacityGroupRepository.save(weekBasedCapacityGroup);
    }

    private void validateFields(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {
        if (!UUIDUtil.checkValidUUID(weekBasedCapacityGroupRequest.getCapacityGroupId())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(weekBasedCapacityGroupRequest.getCapacityGroupId())) {
            throw new BadRequestException("not a valid ID");
        }
    }

    private WeekBasedCapacityGroupEntity convertEntity(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {
        List<Capacity> capacities = weekBasedCapacityGroupRequest
            .getCapacities()
            .stream()
            .map(
                capacitiesDto -> Capacity
                    .builder()
                    .actualCapacity(Double.valueOf(capacitiesDto.getActualCapacity()))
                    .maximumCapacity(Double.valueOf(capacitiesDto.getMaximumCapacity()))
                    .calendarWeek(capacitiesDto.getCalendarWeek())
                    .build()
            )
            .toList();

        List<LikedDemandSeries> likedDemandSeries = weekBasedCapacityGroupRequest
            .getCapacities()
            .stream()
            .map(
                capacitiesDto -> {
                    DemandCategory demandCategory = DemandCategory.builder().build();
                    return LikedDemandSeries.builder().demandCategory(demandCategory).build();
                }
            )
            .toList();

        WeekBasedCapacityGroup weekBasedCapacityGroup = WeekBasedCapacityGroup
            .builder()
            .name(weekBasedCapacityGroupRequest.getName())
            .unityOfMeasure(weekBasedCapacityGroupRequest.getUnityOfMeasure())
            .supplier(weekBasedCapacityGroupRequest.getSupplier())
            .changedAt(weekBasedCapacityGroupRequest.getChangedAt())
            .capacityGroupId(weekBasedCapacityGroupRequest.getCapacityGroupId())
            .customer(weekBasedCapacityGroupRequest.getCustomer())
            .capacities(capacities)
            .likedDemandSeries(likedDemandSeries)
            .build();

        return WeekBasedCapacityGroupEntity.builder().weekBasedCapacityGroup(weekBasedCapacityGroup).build();
    }
}
