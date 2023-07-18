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

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.UnitMeasureRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.UnityOfMeasureService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UnityOfMeasureServiceImpl implements UnityOfMeasureService {

    private final UnitMeasureRepository unitMeasureRepository;

    @Override
    public UnitMeasureEntity findById(UUID id) {
        Optional<UnitMeasureEntity> unitMeasure = unitMeasureRepository.findById(id);

        if (unitMeasure.isEmpty()) {
            throw new BadRequestException("unitMeasure don't exist");
        }
        return unitMeasure.get();
    }
}