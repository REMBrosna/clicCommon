/** Not yet deployed in Prod 08-21-2023 */
UPDATE `T_CK_MST_SERVICE_TYPE` SET `SVCT_NAME` = 'CLICDECLARE' WHERE (`SVCT_ID` = 'CLICDEC');
UPDATE `T_CK_MST_SERVICE_TYPE` SET `SVCT_NAME` = 'CLICDEPOT' WHERE (`SVCT_ID` = 'CLICDEPO');
UPDATE `T_CK_MST_SERVICE_TYPE` SET `SVCT_NAME` = 'CLICGATEPASS' WHERE (`SVCT_ID` = 'CLICGP');

-- 20230807 Added new apps code for clicdo
INSERT INTO `T_CORE_APPS` (`APPS_CODE`, `APPS_DESC`, `APPS_STATUS`, `APPS_LAUNCH_TEXT`, `APPS_LAUNCH_URL`, `APPS_DT_ACTIVE`, `APPS_DT_CREATE`, `APPS_UID_CREATE`, `APPS_DT_LUPD`, `APPS_URI_BASE_PATH`) VALUES ('CKDO', 'ClicDO', 'A', 'Clickargo', 'http://localhost:8080/Clickargo/dashboard', '2022-10-01 00:00:00', '2022-10-01 00:00:00', 'SYS', '2022-10-01 00:00:00', 'http://localhost:8080/clicdo');

UPDATE `T_CORE_APPS` SET `APPS_URI_BASE_PATH` = 'http://localhost:8080/clickargo' WHERE (`APPS_CODE` = 'CK');

INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','ADMIN','A','ACCOUNT ADMIN',NOW(),'SYS',NOW(),'SYS');
INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','OFFICER','A','OFFICER',NOW(),'SYS',NOW(),'SYS');
INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','OPERATIONS','A','OPERATIONS',NOW(),'SYS',NOW(),'SYS');
INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','FINANCE','A','FINANCE',NOW(),'SYS',NOW(),'SYS');
INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','SYS_SUPER_ADMIN','A','SYSTEM SUPER ADMIN',NOW(),'SYS',NOW(),'SYS');
INSERT INTO T_CORE_ROLE(ROLE_APPSCODE, ROLE_ID, ROLE_STATUS, ROLE_DESC, ROLE_DT_CREATE, ROLE_UID_CREATE, ROLE_DT_LUPD, ROLE_UID_LUPD) VALUES ('CKDO','OFFICER_FINANCE','A','SYSTEM SUPER ADMIN',NOW(),'SYS',NOW(),'SYS');

-- execute navigation.sql

-- for the forgot password notification
INSERT INTO `T_CORE_NOTIFICATION_TEMPLATE`(`NTPL_ID`,`NTPL_APPSCODE`,`NTPL_CHANNEL_TYPE`,`NTPL_CONTENT_TYPE`,`NTPL_SUBJECT`,`NTPL_TEMPALTE`,`NTPL_SEQ`,`NTPL_DESC`,`NTPL_STATUS`,`NTPL_UID_CREATE`,`NTPL_DT_CREATE`,`NTPL_UID_LUPD`,`NTPL_DT_LUPD`)  VALUES('CK_NTL_0001','CK','CHN_TYPE_EMAIL','CNT_TYPE_HTML','Forgot Password','<html> <style> html, body{font-family: "Roboto", sans-serif;} #container {width: 800px;border: 1px solid gray;padding: 10px 20px;} .innerContainer {padding: 0px 0px;} #footerContainer {background-color: #f2f3f4;color: darkslategray;padding: 5px 15px;margin-top: 15px;} .button {width: 300px;height: 50px;background-color:steelblue;color: white;margin:50px 0px;justify-content: center;align-items: center;display: flex;} </style> <body> <div id=container> <div id=titleContainer class=innerContainer> <img width=100 height=62 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> <div id=bodyContainer class=innerContainer> <h1>Did you forget your password?</h1>  <a href=:resetPwdLink><div class=button>Reset Password</div></a> <div><p>This link will expire in 24 hours and can be used only once. </p><p>If you do not want to change your password or did not request this, please ignore and delete this message.</p> </div> <div id=footerContainer class=innerContainer> <p><a style=color:inherit href=:privacyStatementLink>Privacy Statement</a></p> <p>:sp_details</p> <img width=50 height=31 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> </div> </body> </html>','1','Notification from Forgot Password link in Login Page','A','SYS',NOW(),'SYS',NOW());

-- for reset password from manage user
INSERT INTO `T_CORE_NOTIFICATION_TEMPLATE`(`NTPL_ID`,`NTPL_APPSCODE`,`NTPL_CHANNEL_TYPE`,`NTPL_CONTENT_TYPE`,`NTPL_SUBJECT`,`NTPL_TEMPALTE`,`NTPL_SEQ`,`NTPL_DESC`,`NTPL_STATUS`,`NTPL_UID_CREATE`,`NTPL_DT_CREATE`,`NTPL_UID_LUPD`,`NTPL_DT_LUPD`)  VALUES('CK_NTL_0002','CK','CHN_TYPE_EMAIL','CNT_TYPE_HTML','Password Reset','<html> <style> html, body{font-family: "Roboto", sans-serif;} #container {width: 800px;border: 1px solid gray;padding: 10px 20px;} .innerContainer {padding: 0px 0px;} #footerContainer {background-color: #f2f3f4;color: darkslategray;padding: 5px 15px;margin-top: 15px;} .button {width: 300px;height: 50px;background-color:steelblue;color: white;margin:50px 0px;justify-content: center;align-items: center;display: flex;} </style> <body> <div id=container> <div id=titleContainer class=innerContainer> <img width=100 height=62 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> <div id=bodyContainer class=innerContainer> <h1>Password Reset by Admin</h1> </div><div><p>Please use the below credentials to login to your account.</p> <br> <p>User ID - :userId, Password: :password</p> <br></div> <div id=footerContainer class=innerContainer> <p><a style=color:inherit href=:privacyStatementLink>Privacy Statement</a></p> <p>:sp_details</p> <img width=50 height=31 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> </div> </body> </html>','2','Reset password from Manage User','A','SYS',NOW(),'SYS',NOW());

-- for new user created
INSERT INTO `T_CORE_NOTIFICATION_TEMPLATE`(`NTPL_ID`,`NTPL_APPSCODE`,`NTPL_CHANNEL_TYPE`,`NTPL_CONTENT_TYPE`,`NTPL_SUBJECT`,`NTPL_TEMPALTE`,`NTPL_SEQ`,`NTPL_DESC`,`NTPL_STATUS`,`NTPL_UID_CREATE`,`NTPL_DT_CREATE`,`NTPL_UID_LUPD`,`NTPL_DT_LUPD`)  VALUES('CK_NTL_0003','CK','CHN_TYPE_EMAIL','CNT_TYPE_HTML','New User Created','<html> <style> html, body{font-family: "Roboto", sans-serif;} #container {width: 800px;border: 1px solid gray;padding: 10px 20px;} .innerContainer {padding: 0px 0px;} #footerContainer {background-color: #f2f3f4;color: darkslategray;padding: 5px 15px;margin-top: 15px;} .button {width: 300px;height: 50px;background-color:steelblue;color: white;margin:50px 0px;justify-content: center;align-items: center;display: flex;} </style> <body> <div id=container> <div id=titleContainer class=innerContainer> <img width=100 height=62 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> <div id=bodyContainer class=innerContainer> <h1>New User Created</h1> </div><div><p>Please use the below credentials to login to your account.</p> <br> <p>User ID - :userId, Password: :password</p> <br></div> <div id=footerContainer class=innerContainer> <p><a style=color:inherit href=:privacyStatementLink>Privacy Statement</a></p> <p>:sp_details</p> <img width=50 height=31 src=https://home.clickargo.com/wp-content/uploads/2022/02/Clic-Logo_Colour-300x187.png> </div> </div> </body> </html>','3','Notification for New User Created','A','SYS',NOW(),'SYS',NOW());

-- changed role description
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'OPERATIONS ADMINISTRATOR' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'OP_ADMIN');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'OPERATIONS OFFICER' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'OFFICER');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'FINANCE VERIFIER' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'FINANCE_VERIFIER');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'FINANCE APPROVER' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'FINANCE_APPROVER');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'FINANCE OFFICER' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'FF_FINANCE');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'ACCOUNT ADMINISTRATOR' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'ADMIN');
UPDATE `T_CORE_ROLE` SET `ROLE_DESC` = 'FINANCE ADMIN' WHERE (`ROLE_APPSCODE` = 'CKT') and (`ROLE_ID` = 'FINANCE_ADMIN');

-- smtp for production
UPDATE `T_CORE_NOTIFICATION_DEVICE` SET `NDEV_CONFIG` = '{\"PORT\":\"587\",\"PASSWORD\":\"pu1999LSE\",\"HOST\":\"smtp.office365.com\",\"USERNAME\":\"Clickargo-DoNotReply@guud.company\",\"SSL\":\"false\", \"TLS_REQ\":\"true\", \"TLS\":\"true\"}' WHERE (`NDEV_ID` = 'MP_DEV_EMAIL') and (`NDEV_APPSCODE` = 'CK');
-- smtp for CK
INSERT INTO `T_CORE_NOTIFICATION_DEVICE` (`NDEV_ID`, `NDEV_APPSCODE`, `NDEV_CHANNEL_TYPE`, `NDEV_CONFIG`, `NDEV_SEQ`, `NDEV_DESC`, `NDEV_STATUS`, `NDEV_UID_CREATE`, `NDEV_DT_CREATE`, `NDEV_UID_LUPD`, `NDEV_DT_LUPD`) VALUES ('MP_DEV_EMAIL', 'CK', 'CHN_TYPE_EMAIL', '{\"PORT\":\"587\",\"PASSWORD\":\"pu1999LSE\",\"HOST\":\"smtp.office365.com\",\"USERNAME\":\"Clickargo-DoNotReply@guud.company\",\"SSL\":\"false\", \"TLS_REQ\":\"true\", \"TLS\":\"true\"}', '1', 'Email Device for CamelOne', 'A', 'SYS', '2018-01-01 00:00:00', 'SYS', '2018-01-01 00:00:00');

-- UPDATE `T_CORE_NOTIFICATION_DEVICE` SET `NDEV_CONFIG` = '{\"PORT\":\"587\",\"PASSWORD\":\"pu1999LSE\",\"HOST\":\"smtp.office365.com\",\"USERNAME\":\"Clickargo-DoNotReply@guud.company\",\"SSL\":\"false\", \"TLS_REQ\":\"true\", \"TLS\":\"true\"}' WHERE (`NDEV_ID` = 'MP_DEV_EMAIL') and (`NDEV_APPSCODE` = 'CKT');

/** 23082023 Nina New table for payment txn log*/
CREATE TABLE `T_CK_PAYMENT_TXN_LOG` (
  `PTXL_ID` varchar(35) NOT NULL COMMENT 'PAYMENT TXN LOG ID - UUID',
  `PTXL_TXN_REF` varchar(35) NOT NULL COMMENT 'PAYMENT TXN LOG REF NO. - REFERENCE T_CK_PAYMENT_TXN:PTX_ID',
  `PTXL_TXN_STATE` varchar(35) DEFAULT NULL COMMENT 'PAYMENT TXN STATE',
  `PTXL_REMARKS` mediumtext COMMENT 'REMARKS',
  `PTXL_DT_CREATE` datetime DEFAULT NULL COMMENT 'RECORD CREATED DATE',
  `PTXL_UID_CREATE` varchar(35) DEFAULT NULL COMMENT 'RECORD CREATED USER',
  `PTXL_DT_LUPD` datetime DEFAULT NULL COMMENT 'RECORD LAST UPDATED DATE',
  `PTXL_UID_LUPD` varchar(35) DEFAULT NULL COMMENT 'RECORD LAST UPDATED USER',
  PRIMARY KEY (`PTXL_ID`),
  KEY `FK_CK_PAYMENT_TXN_LOG_REF_CK_PAYMENT_TXN` (`PTXL_TXN_REF`),
  CONSTRAINT `FK_CK_PAYMENT_TXN_LOG_REF_CK_PAYMENT_TXN` FOREIGN KEY (`PTXL_TXN_REF`) REFERENCES `T_CK_PAYMENT_TXN` (`PTX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='PAYMENT TXN LOG TABLE';

-- executed in uat on 23-Aug-2023 --
-- executed in PROD on 26-Aug-2023 --
-- do not add below this line --