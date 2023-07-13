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

package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_base_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @Column(name = "bpn")
    private String bpn;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "my_company")
    private String myCompany;

    @Column(name = "edc_url")
    private String edcUrl;
}
