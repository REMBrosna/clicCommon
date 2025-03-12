
delete from clickargo2.T_CORE_GRANT_ROLE_EX gre
	where gre.GNTRLEX_PERM_ID in 
		(select PERM_ID from clickargo2.T_CORE_PERM_EX 
			where PERM_MENU in 
				(SELECT MENU_ID FROM clickargo2.T_CORE_MENU_EX 
					where MENU_PATH like '%ratetable%' or MENU_PATH like '%sageintegrations%' or MENU_PATH like '%bankaccounts%'
						or MENU_PATH like '%staticva%' or MENU_PATH like '%outboundpayments%'
						or MENU_PATH like '%inboundpayments%' or MENU_PATH like '%order-termination%'
						or MENU_PATH like '%docverification%' or MENU_PATH like '%trackTrackEnterExit%') );

delete from clickargo2.T_CORE_PERM_EX 
			where PERM_MENU in 
				(SELECT MENU_ID FROM clickargo2.T_CORE_MENU_EX 
					where MENU_PATH like '%ratetable%' or MENU_PATH like '%sageintegrations%' or MENU_PATH like '%bankaccounts%'
						or MENU_PATH like '%staticva%' or MENU_PATH like '%outboundpayments%'
						or MENU_PATH like '%inboundpayments%' or MENU_PATH like '%order-termination%'
						or MENU_PATH like '%docverification%' or MENU_PATH like '%trackTrackEnterExit%');

delete FROM clickargo2.T_CORE_MENU_EX 
					where MENU_PATH like '%ratetable%' or MENU_PATH like '%sageintegrations%' or MENU_PATH like '%bankaccounts%'
						or MENU_PATH like '%staticva%' or MENU_PATH like '%outboundpayments%'
						or MENU_PATH like '%inboundpayments%' or MENU_PATH like '%order-termination%'
						or MENU_PATH like '%docverification%' or MENU_PATH like '%trackTrackEnterExit%';

-- add menus for FF-CO

INSERT INTO `clickargo2`.`T_CORE_GRANT_ROLE_EX` (`GNTRLEX_APPS_CODE`, `GNTRLEX_ROLE_ID`, `GNTRLEX_PERM_ID`, `GNTRLEX_STATUS`, `GNTRLEX_DT_CREATE`, `GNTRLEX_UID_CREATE`) 
VALUES ('CKT', 'FF_CO_ADMIN', 'PM027', 'A', '2024-01-08 06:00:19', 'SYS');

INSERT INTO `clickargo2`.`T_CORE_GRANT_ROLE_EX` (`GNTRLEX_APPS_CODE`, `GNTRLEX_ROLE_ID`, `GNTRLEX_PERM_ID`, `GNTRLEX_STATUS`, `GNTRLEX_DT_CREATE`, `GNTRLEX_UID_CREATE`) 
VALUES ('CKT', 'FF_CO_ADMIN', 'PM036', 'A', '2024-01-08 06:00:19', 'SYS');

INSERT INTO `clickargo2`.`T_CORE_GRANT_ROLE_EX` (`GNTRLEX_APPS_CODE`, `GNTRLEX_ROLE_ID`, `GNTRLEX_PERM_ID`, `GNTRLEX_STATUS`, `GNTRLEX_DT_CREATE`, `GNTRLEX_UID_CREATE`) 
VALUES ('CKT', 'FF_CO_ADMIN', 'PM037', 'A', '2024-01-08 06:00:19', 'SYS');

INSERT INTO `clickargo2`.`T_CORE_GRANT_ROLE_EX` (`GNTRLEX_APPS_CODE`, `GNTRLEX_ROLE_ID`, `GNTRLEX_PERM_ID`, `GNTRLEX_STATUS`, `GNTRLEX_DT_CREATE`, `GNTRLEX_UID_CREATE`) 
VALUES ('CKT', 'FF_CO_ADMIN', 'PM038', 'A', '2024-01-08 06:00:19', 'SYS');
