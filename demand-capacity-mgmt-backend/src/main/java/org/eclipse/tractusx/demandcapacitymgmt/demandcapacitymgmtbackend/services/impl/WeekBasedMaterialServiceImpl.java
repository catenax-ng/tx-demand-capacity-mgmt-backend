/*
 * Copyright (c) 2021,2023 Contributors to the Eclipse Foundation
 *
 *  See the NOTICE file(s) distributed with this work for additional information regarding copyright ownership.
 *
 *  This program and the accompanying materials are made available under the terms of the Apache License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 */

package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedMaterialDemandRequestDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.SupplierEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedMaterialDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekDemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.SupplierRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.WeekBasedMaterialDemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedMaterialService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeekBasedMaterialServiceImpl implements WeekBasedMaterialService {

    private final WeekBasedMaterialDemandRepository weekBasedMaterialDemandRepository;

    private final SupplierRepository supplierRepository;

    @Override
    public void createWeekBasedMaterial(WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto) {
        validateFields(weekBasedMaterialDemandRequestDto);
        WeekBasedMaterialDemandEntity weekBasedMaterialDemand = convertEntity(weekBasedMaterialDemandRequestDto);
        weekBasedMaterialDemandRepository.save(weekBasedMaterialDemand);

        List<WeekBasedMaterialDemandEntity> weekBasedMaterialDemandEntities = weekBasedMaterialDemandRepository.findAll();
    }

    @Override
    public void sendWeekBasedMaterial() {
        Optional<SupplierEntity> supplierEntityOpt = supplierRepository.findById(1l);

        if (supplierEntityOpt.isPresent()) {
            SupplierEntity supplierEntity = supplierEntityOpt.get();
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = supplierEntity.getEdcUrl();

            //TODO create the Actual Demand and send to the supplier
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        }
    }

    @Override
    public void receiveWeekBasedMaterial() {
        List<WeekBasedMaterialDemandEntity> weekBasedMaterialDemandEntities = weekBasedMaterialDemandRepository.getAllByViewed(
            false
        );

        //todo define if we are going to send email or notification when we have new requestMaterials
        weekBasedMaterialDemandEntities.forEach(
            weekBasedMaterialDemandEntity -> weekBasedMaterialDemandEntity.setViewed(true)
        );

        weekBasedMaterialDemandRepository.saveAll(weekBasedMaterialDemandEntities);
    }

    private void validateFields(WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto) {
        if (!UUIDUtil.checkValidUUID(weekBasedMaterialDemandRequestDto.getMaterialDemandId())) {
            throw new BadRequestException("not a valid ID");
        }

        weekBasedMaterialDemandRequestDto
            .getDemandSeries()
            .forEach(
                demandWeekSeriesDto -> {
                    demandWeekSeriesDto
                        .getDemands()
                        .forEach(
                            demandSeriesDto -> {
                                if (!DataConverterUtil.itsMonday(demandSeriesDto.getCalendarWeek())) {
                                    throw new BadRequestException("not a valid date");
                                }
                            }
                        );
                }
            );
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
