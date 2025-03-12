use tmp;

-- 1 company;
DROP TABLE IF EXISTS tmp.TMP_COMPANY;

create table tmp.TMP_COMPANY as SELECT
    `uuid` AS ID,
    `name` AS NAME,
    (
        SELECT
            # convert ct.name
            # shipper to CO
            # cargo owner to CO
            # cargo owner clicktruck to CO
            # freight forwarder to FF
            # freight fowarder to FF
            # terminal operator to TO
            # truck company to TC

            JSON_ARRAYAGG(CASE `ct`.`name`
                              WHEN 'shipper'
                                  THEN 'CO'
                              WHEN 'cargo owner'
                                  THEN 'CO'
                              WHEN 'cargo owner clicktruck'
                                  THEN 'CO'
                              WHEN 'freight forwarder'
                                  THEN 'FF'
                              WHEN 'freight fowarder'
                                  THEN 'FF'
                              WHEN 'terminal operator'
                                  THEN 'TO'
                              WHEN 'truck company'
                                  THEN 'TC'
                              ELSE `ct`.`name`
                          END)
        FROM prod_clickargo_company.`company_types` `ct` WHERE `name` NOT IN ('depo', 'insurance', 'nvocc') AND `ct`.`company_uuid` = `companies`.`uuid` AND `ct`.`deleted_at` IS NULL
    ) AS TYPE,
    `tax_number` AS VAT_NO,
    (
        SELECT `address` FROM prod_clickargo_company.`addresses` `a` WHERE `a`.`company_uuid` = `companies`.`uuid` AND `a`.`deleted_at` IS NULL ORDER BY `a`.`id` ASC LIMIT 1
    ) AS ADDR1,
    null AS ADDR2,
    null AS ADDR3,
    (
        SELECT `postal_code` FROM prod_clickargo_company.`addresses` `a` WHERE `a`.`company_uuid` = `companies`.`uuid` AND `a`.`deleted_at` IS NULL ORDER BY `a`.`id` ASC LIMIT 1
    ) AS PCODE,
    (
        SELECT `city` FROM prod_clickargo_company.`addresses` `a` WHERE `a`.`company_uuid` = `companies`.`uuid` AND `a`.`deleted_at` IS NULL ORDER BY `a`.`id` ASC LIMIT 1
    ) AS CITY,
    (
        SELECT `state` FROM prod_clickargo_company.`addresses` `a` WHERE `a`.`company_uuid` = `companies`.`uuid` AND `a`.`deleted_at` IS NULL ORDER BY `a`.`id` ASC LIMIT 1
    ) AS PROV,
    null AS CTYCODE,
    `phone` AS TEL,
    null AS FAX,
    `email` AS EMAIL,
    `created_at` AS REG_DATE,
    ascent_email as ASCENT_EMAIL,
	ascent_company_id as ASCENT_COMPANY_ID,
	ascent_password as ASCENT_PASSWORD
FROM prod_clickargo_company.`companies`
WHERE `companies`.`deleted_at` IS NULL
AND
    EXISTS(
        SELECT
            1
        FROM
            `prod_clickargo_company`.`company_types` ct
        WHERE
            ct.company_uuid = `prod_clickargo_company`.`companies`.`uuid`
            AND `name` NOT IN ('depo', 'insurance', 'nvocc')
            AND ct.deleted_at IS NULL
    )
;
-- 2 user:
create table tmp.TMP_USER as SELECT
    `uuid` AS UID,
    `company_uuid` AS COMPANY_ID,
    null AS NID,
    null AS PWD,
    `email` AS EMAIL,
    DATE_FORMAT(created_at, '%Y-%m-%d') as REG_DATE
FROM
    `prod_clickargo_gateway`.`users`
WHERE
    `deleted_at` IS NULL
    AND `company_uuid` IN (
        SELECT
            `uuid`
        FROM
            `prod_clickargo_company`.`companies`
        WHERE
            `deleted_at` IS NULL
            AND EXISTS(
                SELECT
                    1
                FROM
                    `prod_clickargo_company`.`company_types` `ct`
                WHERE
                    `ct`.`company_uuid` = `prod_clickargo_company`.`companies`.`uuid`
                    AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                    AND `ct`.`deleted_at` IS NULL
            )
    );
    
-- 3 role
-- 4 truck;
DROP TABLE IF EXISTS tmp.TMP_TRUCK ;

create table tmp.TMP_TRUCK as SELECT
    `t`.`uuid` AS ID,
    t.team_uuid as DEP_UUID,
    `t`.`type` AS TYPE,
    null AS STATE,
    `t`.company_uuid AS COMPANY,
    `t`.license_number AS PLATE_NO,
    null AS CLASS,
    `t`.`length` AS LENGTH,
    `t`.`width` AS WIDTH,
    `t`.`height` AS HEIGHT,
    `t`.`weight` AS WEIGHT,
    `t`.`cbm` AS VOLUME,
    `chassis`.license_number AS CHASSIS_NO,
    null AS CHASSIS_TYPE,
    0 AS IS_MAINTENANCE,
    null AS REMARKS,
    `t`.`reference_number` AS GPS_IMEI
FROM
    `prod_clicktruck`.`clicktruck_trucks` `t`
    LEFT JOIN `prod_clicktruck`.`clicktruck_truck_has_chassis` `cc`ON `t`.`uuid` = `cc`.`truck_uuid`
    LEFT JOIN `prod_clicktruck`.`clicktruck_chassis` `chassis`ON `cc`.`chassis_uuid` = `chassis`.`uuid`
WHERE
    `t`.`deleted_at` IS NULL
    AND `t`.`company_uuid` IN (
        SELECT
            `uuid`
        FROM
            `prod_clickargo_company`.`companies`
        WHERE
            `deleted_at` IS NULL
            AND EXISTS(
                SELECT
                    1
                FROM
                    `prod_clickargo_company`.`company_types` `ct`
                WHERE
                    `ct`.`company_uuid` = `prod_clickargo_company`.`companies`.`uuid`
                    AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                    AND `ct`.`deleted_at` IS NULL
            )
    ) group by `t`.`uuid` ;
    
-- 5 Driver
create table tmp.TMP_DRV as select
    `uuid` AS UID,
    `name` AS NAME,
    `username` AS USER_NAME,
    `company_uuid` AS COMPANY,
    null AS LICENSE_NO,
    null AS LICENSE_EXPIRE,
    `email` AS EMAIL,
    null AS PHONE,
    null AS APP_ID,
    null AS APP_PASSWORD
FROM
    `prod_clicktruck`.`clicktruck_drivers`
WHERE
    `deleted_at` IS NULL
    AND `company_uuid` IN (
        SELECT
            `uuid`
        FROM
            `prod_clickargo_company`.`companies`
        WHERE
            `deleted_at` IS NULL
            AND EXISTS(
                SELECT
                    1
                FROM
                    `prod_clickargo_company`.`company_types` `ct`
                WHERE
                    `ct`.`company_uuid` = `prod_clickargo_company`.`companies`.`uuid`
                    AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                    AND `ct`.`deleted_at` IS NULL
            )
    );
    
-- 6 credit limit
create table tmp.TMP_CREDIT as select
    'CLICTRUCK' as SERVICE_TYPE,
    cargo_owner_uuid as COMPANY,
    (CASE status
        WHEN 0
            THEN 'EXPIRED'
        WHEN 1
            THEN 'ACTIVE'
        WHEN 2
            THEN 'SUSPENDED'
        ELSE 'UTILIZED'
    END) as STATE,
    credit_limit_approved as AMT,
    'IDR' as CCY,
    (credit_limit_approved - credit_limit_amount) as AMT_USED,
    DATE_FORMAT(created_at, '%Y-%m-%d') as DT_START,
    DATE_FORMAT(validity_date, '%Y-%m-%d') as DT_END,
    'sys' as USR_VERIFY,
    now() as DT_VERIFY,
    'sys' as USR_APPROVE,
    now() as DT_APPROVE
FROM `prod_clickargo_company`.companies_credit_terms
where deleted_at is null and is_current = 1
and (cargo_owner_uuid in (
        select uuid from `prod_clickargo_company`.companies
                    where deleted_at is null
                      and exists(SELECT 1
                                 FROM `prod_clickargo_company`.company_types ct
                                 WHERE ct.company_uuid = companies.uuid
                                   AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                                   AND ct.deleted_at IS NULL)
    ) OR truck_company_uuid in (
              select uuid from `prod_clickargo_company`.companies
                    where deleted_at is null
                      and exists(SELECT 1
                                 FROM `prod_clickargo_company`.company_types ct
                                 WHERE ct.company_uuid = companies.uuid
                                   AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                                   AND ct.deleted_at IS NULL)
    )
    ) group by uuid;

-- 7 location

-- 8 rate;
create table tmp.TMP_RATE as SELECT c2.name as 'CO_FF', c1.name as 'TO_COMPANY'
, pr.start_area as 'From_Loc', pr.end_area as 'To_Loc', pr.truck_type as 'Veh_Type'
, pr.price as 'CO_Price', pr.truck_price as 'TO_Price'
FROM prod_clicktruck.clicktruck_job_price_rates pr left join prod_clickargo_company.contracts ct on pr.contract_id = ct.contract_id 
left join prod_clickargo_company.companies c1 on ct.first_party_uuid = c1.uuid
left join prod_clickargo_company.companies c2 on ct.second_party_uuid = c2.uuid
where c1.name is not null and c2.name is not null
group by pr.uuid;

-- 9 contract
Drop table if exists tmp.TMP_CONTRACT;
create table tmp.TMP_CONTRACT as select
    `c`.UUID as ID,
    'IDR' as CCY,
    c.contract_id as CONTRACT_NAME,
    DATE_FORMAT(`c`.`start_date`, '%Y-%m-%d') as DT_START,
    DATE_FORMAT(`c`.`end_date`, '%Y-%m-%d') as DT_END,
    `c`.`first_party_uuid` as "TO",
    `cts`.to_pltfee_amt as TO_PLTFEE_AMT,
    `cts`.to_pltfee_type as TO_PLTFEE_TYPE,
    `cts`.to_addtax_amt as TO_ADDTAX_AMT,
    `cts`.to_addtax_type as TO_ADDTAX_TYPE,
    `cts`.to_whtax_amt as TO_WHTAX_AMT,
    `cts`.to_whtax_type as TO_WHTAX_TYPE,
    NULL as TO_PAYTERM,
	`c`.`second_party_uuid` as "CO_COMPANY",
    `c`.`third_party_uuid` as "FF_COMPANY",
    `c`.invoice_issued_to as CONTRACT_INVOICE,
    `cc`.invoice_issued_to as CONTRACT_COUNTRIES_INVOICE,
    (CASE 
		WHEN `cc`.invoice_issued_to IS NOT NULL 
			THEN 
				CASE `cc`.`invoice_issued_to`
					WHEN 'CO'
						THEN (select IF(second_party_uuid, second_party_uuid, 'INV ISSUED TO FF BECAUSE CONTRACT DOESNT HAVE CO OR SECOND PARTY') from `prod_clickargo_company`.contracts c2 where c2.uuid = cc.contract_uuid)
					ELSE ''
				END
		ELSE
			CASE `c`.`invoice_issued_to`
				WHEN 'CO'
					THEN IF(`c`.`second_party_uuid`, `c`.`second_party_uuid`, 'INV ISSUED TO FF BECAUSE CONTRACT DOESNT HAVE CO OR SECOND PARTY')
				ELSE ''
			END
	END) as CO,
    (CASE 
		WHEN `cc`.invoice_issued_to IS NOT NULL 
			THEN 
				CASE `cc`.`invoice_issued_to`
					WHEN 'FF'
						THEN (select third_party_uuid  from `prod_clickargo_company`.contracts c2 where c2.uuid = cc.contract_uuid)
					ELSE ''
				END
		ELSE
			CASE `c`.`invoice_issued_to`
				WHEN 'FF'
					THEN  `c`.`third_party_uuid`
				ELSE ''
			END
	END) as FF,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `c`.second_party_uuid
        ELSE `c`.`third_party_uuid`
    END) as CO_FF,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_pltfee_amt
        ELSE `cts`.ff_pltfee_amt
    END) as CO_FF_PLTFEE_AMT,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_pltfee_type
        ELSE `cts`.ff_pltfee_type
    END) as CO_FF_PLTFEE_TYPE,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_addtax_amt
        ELSE `cts`.ff_addtax_amt
    END) as CO_FF_ADDTAX_AMT,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_addtax_type
        ELSE `cts`.ff_addtax_type
    END) as CO_FF_ADDTAX_TYPE,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_whtax_amt
        ELSE `cts`.ff_whtax_amt
    END) as CO_FF_WHTAX_AMT,
    (CASE `cc`.`invoice_issued_to`
        WHEN 'CO'
            THEN `cts`.co_whtax_type
        ELSE `cts`.ff_whtax_type
    END) as CO_FF_WHTAX_TYPE,
    NULL as CO_FF_PAYTERM
FROM `prod_clickargo_company`.contract_countries cc right join `prod_clickargo_company`.contracts c on `cc`.contract_uuid = `c`.uuid
left join (
    select
            cts_to.`contract_country_uuid`,
            platform_fee as to_pltfee_amt,
            (CASE platform_fee_is_percent
                WHEN 1
                    THEN 'P'
                ELSE 'F'
            END) as to_pltfee_type,
            additional_tax_value as to_addtax_amt,
            (CASE additional_tax_is_percent
                WHEN 1
                    THEN 'P'
                ELSE 'F'
            END) as to_addtax_type,
            withholding_tax as to_whtax_amt,
            (CASE withholding_tax_is_percent
                WHEN 1
                    THEN 'P'
                ELSE 'F'
            END) as to_whtax_type,
            cts_co.co_pltfee_amt,
            cts_co.co_pltfee_type,
            cts_co.co_addtax_amt,
            cts_co.co_addtax_type,
            cts_co.co_whtax_amt,
            cts_co.co_whtax_type,
            cts_ff.ff_pltfee_amt,
            cts_ff.ff_pltfee_type,
            cts_ff.ff_addtax_amt,
            cts_ff.ff_addtax_type,
            cts_ff.ff_whtax_amt,
            cts_ff.ff_whtax_type
        from `prod_clickargo_company`.contract_tax_settings cts_to
            left join (
                        select
                        `contract_country_uuid`,
                        platform_fee as co_pltfee_amt,
                        (CASE platform_fee_is_percent
                            WHEN 1
                                THEN 'P'
                            ELSE 'F'
                        END) as co_pltfee_type,
                        additional_tax_value as co_addtax_amt,
                        (CASE additional_tax_is_percent
                            WHEN 1
                                THEN 'P'
                            ELSE 'F'
                        END) as co_addtax_type,
                        withholding_tax as co_whtax_amt,
                        (CASE withholding_tax_is_percent
                            WHEN 1
                                THEN 'P'
                            ELSE 'F'
                        END) as co_whtax_type
                    from `prod_clickargo_company`.contract_tax_settings cts_to
                    where company_type = 'CO'
                    and deleted_at is null
                    ) cts_co on cts_to.`contract_country_uuid` = cts_co.`contract_country_uuid`
        left join
            (select `contract_country_uuid`,
                    platform_fee as ff_pltfee_amt,
                    (CASE platform_fee_is_percent
                        WHEN 1
                            THEN 'P'
                        ELSE 'F'
                    END) as ff_pltfee_type,
                    additional_tax_value as ff_addtax_amt,
                    (CASE additional_tax_is_percent
                        WHEN 1
                            THEN 'P'
                        ELSE 'F'
                    END) as ff_addtax_type,
                    withholding_tax as ff_whtax_amt,
                    (CASE withholding_tax_is_percent
                        WHEN 1
                            THEN 'P'
                        ELSE 'F'
                    END) as ff_whtax_type
                from `prod_clickargo_company`.contract_tax_settings cts_ff
                where company_type = 'FF'
                and deleted_at is null
                ) cts_ff on cts_to.`contract_country_uuid` = cts_ff.`contract_country_uuid`
        where company_type = 'TC'
        and `deleted_at` is null
        and `module` = 'clictruck'
    ) cts on `cc`.uuid = `cts`.`contract_country_uuid`
where `c`.`deleted_at` is NULL
and `cc`.deleted_at is NULL
and (
    `c`.first_party_uuid in (
        select uuid
               from `prod_clickargo_company`.companies
               where deleted_at is null
                 and exists(SELECT 1
                            FROM `prod_clickargo_company`.company_types ct
                            WHERE ct.company_uuid = companies.uuid
                              AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                              AND ct.deleted_at IS NULL)
    ) AND (
        `c`.`second_party_uuid` in (
            select uuid
               from `prod_clickargo_company`.companies
               where deleted_at is null
                 and exists(SELECT 1
                            FROM `prod_clickargo_company`.company_types ct
                            WHERE ct.company_uuid = companies.uuid
                              AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                              AND ct.deleted_at IS NULL)
        ) OR
        `c`.`third_party_uuid` in (
            select `uuid`
               from `prod_clickargo_company`.companies
               where `deleted_at` is null
                 and EXISTS(SELECT 1
                            FROM `prod_clickargo_company`.company_types ct
                            WHERE ct.company_uuid = companies.uuid
                              AND `name` NOT IN ('depo', 'insurance', 'nvocc')
                              AND ct.deleted_at IS NULL)
        )
    )
) ;