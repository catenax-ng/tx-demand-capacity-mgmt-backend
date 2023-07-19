/*
 * Copyright (c) 2023 BMW AG
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the Apache License, Version 2.0 which is available at https://urldefense.com/v3/__https://www.apache.org/licenses/LICENSE-2.0__;!!AaIhyw!uYX7Bu-1kWQLP4kPuodSlHQSD2bmG4M3lmTLhsx-Iwhh_yOSI4BS0VxdlZ5zIHsFyJaiPPcP9NsfPv4iqxVC7JXn_Df2pyIcTLRQ4aI$ .
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CustomerEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.SupplierEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedCapacityGroupEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.Capacity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.DemandCategory;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.LikedDemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedCapacityGroup;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.CustomerRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.WeekBasedCapacityGroupRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedCapacityGroupService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class WeekBasedCapacityGroupServiceImpl implements WeekBasedCapacityGroupService {

    private final WeekBasedCapacityGroupRepository weekBasedCapacityGroupRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void createWeekBasedCapacityGroup(List<WeekBasedCapacityGroupRequest> weekBasedCapacityGroupRequestList) {
        weekBasedCapacityGroupRequestList.forEach(
            weekBasedCapacityGroupRequest -> {
                validateFields(weekBasedCapacityGroupRequest);

                WeekBasedCapacityGroupEntity weekBasedCapacityGroup = convertEntity(weekBasedCapacityGroupRequest);
                weekBasedCapacityGroupRepository.save(weekBasedCapacityGroup);
            }
        );
    }

    @Override
    public void receiveWeekBasedCapacityGroup() {
        List<WeekBasedCapacityGroupEntity> weekBasedCapacityGroupEntities = weekBasedCapacityGroupRepository.getAllByViewed(
            false
        );

        //todo define if we are going to send email or notification when we have new requestMaterials
        weekBasedCapacityGroupEntities.forEach(
            weekBasedCapacityGroupEntity -> {
                weekBasedCapacityGroupEntity.setViewed(true);
            }
        );

        weekBasedCapacityGroupRepository.saveAll(weekBasedCapacityGroupEntities);
    }

    @Override
    public void sendWeekBasedCapacityGroup() {
        Optional<CustomerEntity> supplierEntityOpt = customerRepository.findById(1l);

        //TODO we still dont have defined the demand or the capacity structure yet, this is just an example of the flux
        if (supplierEntityOpt.isPresent()) {
            CustomerEntity supplierEntity = supplierEntityOpt.get();

            //todo put this part of the code in the ConsumerHTTP class
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = supplierEntity.getEdcUrl();

            //TODO create the Actual Demand and send to the supplier
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        }
    }

    private void validateFields(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {
        if (!UUIDUtil.checkValidUUID(weekBasedCapacityGroupRequest.getCapacityGroupId())) {
            throw new BadRequestException("not a valid ID");
        }
    }

    private WeekBasedCapacityGroupEntity convertEntity(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {
        List<Capacity> capacities = weekBasedCapacityGroupRequest
            .getCapacities()
            .stream()
            .map(
                capacitiesDto ->
                    Capacity
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
