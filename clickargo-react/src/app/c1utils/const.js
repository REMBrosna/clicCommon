import { Icon } from "@material-ui/core";
import { Assignment, Build, FileCopy, Schedule } from "@material-ui/icons";
import React from "react";

export const Status = {
    ACK: { code: "ACK", desc: "Acknowledged" },
    APP: { code: "APP", desc: "Approved" },
    NEW: { code: "NEW", desc: "New" },
    DRF: { code: "DRF", desc: "Draft" },
    REJ: { code: "REJ", desc: "Rejected" },
    RET: { code: "RET", desc: "Returned" },
    EXP: { code: "EXP", desc: "Expired" },
    VER: { code: "VER", desc: "Verified" },
    PEN: { code: "PEN", desc: "Pending Payment" },
    PAY: { code: "PAY", desc: "Paid" },
    AMN: { code: "AMN", desc: "Amended" },
    SUB: { code: "SUB", desc: "Submitted" },
    ORT: { code: "ORT", desc: "Officer Returned" },
    PRA: { code: "PRA", desc: "Pre Approved" },
    INV: { code: "INV", desc: "Invoice Generated" },
};

export const CommitteeAccount = {
    BRDRCUST: { code: "BRDRCUST", type: "ACC_TYPE_CUSTOMS", name: "PPAP Border Customs" },
    BRDRIMMN: { code: "BRDRIMMN", type: "ACC_TYPE_IMMIGRATION", name: "PPAP Border Immigration" },
    BRDRQRNT: { code: "BRDRQRNT", type: "ACC_TYPE_QUARANTINE", name: "PPAP Border Quarantine" },
    PASCUST: { code: "PASCUST", type: "ACC_TYPE_CUSTOMS", name: "PAS Customs" },
    PASIMMN: { code: "PASIMMN", type: "ACC_TYPE_IMMIGRATION", name: "PAS Immigration" },
    PASQRNT: { code: "PASQRNT", type: "ACC_TYPE_QUARANTINE", name: "PAS Quarantine" },
    PASPORT: { code: "PASPORT", type: "ACC_TYPE_PORT", name: "PAS Port Authority" },
    GDCE: { code: "GDCE", type: "ACC_TYPE_CUSTOMS", name: "General Department of Customs and Excise" },
    GDI: { code: "GDI", type: "ACC_TYPE_IMMIGRATION", name: "General Department of Immigration" },
    CDCD: { code: "CDCD", type: "ACC_TYPE_QUARANTINE", name: "Communicable Diseases Control Department" },
    MMD: { code: "MMD", type: "ACC_TYPE_MPWT", name: "MMD" },
    PPAPCUST: { code: "PPAPCUST", type: "ACC_TYPE_CUSTOMS", name: "PPAP Customs" },
    PPAPIMMN: { code: "PPAPIMMN", type: "ACC_TYPE_IMMIGRATION", name: "PPAP Immigration" },
    PPAPQRNT: { code: "PPAPQRNT", type: "ACC_TYPE_QUARANTINE", name: "PPAP Quarantine" },
    PPAPPORT: { code: "PPAPPORT", type: "ACC_TYPE_PORT", name: "PPAP Port Authority" },

};

export const VoyageTypes = {
    IN: { code: "INWARD", desc: "Inward" },
    OUT: { code: "OUTWARD", desc: "Outward" },
};

export const FeeTypes = {
    DO: { code: "PER DO", desc: "Per DO" },
    BL: { code: "PER BL", desc: "Per BL" },
};

export const PaymentTypes = {
    CLICPAY: { code: "CLICPAY", desc: "ClicPay" },
    CREDIT: { code: "CREDIT", desc: "Credit" },
};

export const BillingTypes = {
    IMMEDIATE: { code: "IMMDT", desc: "Immediate" },
    WEEKLY: { code: "WEEK", desc: "Weekly" },
    MONTHLY: { code: "MONTH", desc: "Monthly" },
};

export const ApplicationType = {
    VP: { code: "VP", desc: "Vessel Profile" },
    SR: { code: "SR", desc: "Ship Registration" },
    VC: { code: "VC", desc: "Vessel Call" },
    EP: { code: "EP", desc: "Entry Permit" },
    PAS: { code: "PAS", desc: "Ship Pre-Arrival Security Information Notice" },
    PO: { code: "PO", desc: "Pilot Order" },
    DOS: { code: "DOS", desc: "Declaration of Security" },
    SSCC: { code: "SSCC", desc: "Ship Sanitation Control Certificate" },
    SSCEC: { code: "SSCEC", desc: "Ship Sanitation Control Excemption Certificate" },
    PAN: { code: "PAN", desc: "Pre-Arrival Notice" },
    AD: { code: "AD", desc: "Arrival Declaration" },
    DD: { code: "DD", desc: "Departure Declaration" },
    ADSUB: { code: "ADSUB", desc: "Arrival Declaration" },
    DDSUB: { code: "DDSUB", desc: "Departure Declaration" },
    PAY: { code: "PAY", desc: "Payment" },
};

export const mainPort = {
    KHKOS: { code: "KHKOS", desc: "KHKOS- Sihanouville Port" },
    KHPNH: { code: "KHPNH", desc: "KHPNH- Phnom Penh Autonomous Port" },
};

export const ApplicationTypePath = {
    SR: { code: "SR", path: "/vessel/shipRegistration/view" },
    VC: { code: "VC", path: "/applications/vesselCall/view" },
    EP: { code: "EP", path: "/applications/entryPermit/view" },
    PAS: { code: "PAS", path: "/applications/cargoSecurityInfo/view" },
    PO: { code: "PO", path: "/applications/pilotOrder/view" },
    DOS: { code: "DOS", path: "/applications/dos/view" },
    PAN: { code: "PAN", path: "/applications/preArrivalNotice/view" },
    AD: { code: "AD", path: "/applications/arrivalDeclaration/view" },
    DD: { code: "DD", path: "/applications/departureDeclaration/view" },
    SSCC: { code: "SSCC", path: "/applications/sscec/sscc/view" },
    SSCEC: { code: "SSCEC", path: "/applications/sscec/sscc/view" },
    ADSUB: { code: "ADSUB", path: "/applications/arrivalDeclaration/view" },
    DDSUB: { code: "DDSUB", path: "/applications/departureDeclaration/view" },
};

export const RegistrationStatus = {
    EXPIRED: { code: "X", desc: "Expired" },
    PENDING_APPROVAL: { code: "P", desc: "Pending Approval" },
    PENDING_ACCCN_ACTIVATION: { code: "C", desc: "Pending Account Activation" },
    APPROVED: { code: "V", desc: "Approved" },
    REJECTED: { code: "R", desc: "Rejected" }

};

export const RecordStatus = {
    NEW: { code: "N", desc: "New" },
    ACTIVE: { code: "A", desc: "Active" },
    INACTIVE: { code: "I", desc: "InActive" },
    SUSPENDED: { code: "S", desc: "Suspended" }
};

export const AccountTypes = {
    ACC_TYPE_SL: { code: "ACC_TYPE_ADMIN", desc: "SHIPPING LINE" },
    ACC_TYPE_FF: { code: "ACC_TYPE_FF", desc: "ACC_TYPE_FF" },
    ACC_TYPE_CO: { code: "ACC_TYPE_CO", desc: "CARGO OWNER" },
    ACC_TYPE_CK: { code: "CLICKARGO", desc: "CLICKARGO" }
};

export const AccountTypesColumns = {
    ACC_TYPE_SHIP_LINE: { code: "ACC_TYPE_SHIP_LINE", desc: "SL" },
    ACC_TYPE_SHIP_AGENT: { code: "ACC_TYPE_SHIP_AGENT", desc: "SA" }
};

export const Applications = {
    CONE: { code: "CONE", desc: "CamelOne Portal" },
    CPEDI: { code: "CPEDI", desc: "Cambodia PortEDI" },
    MSW: { code: "MSW", desc: "Marinetime Single Window" },
};

export const ShipType = {
    CONTAINER: { code: "CONTAINER", desc: "Container" },
    CARGO: { code: "GENERAL_CARGO", desc: "General Cargo" },
    PASSENGER: { code: "PASSENGER_CRUISE", desc: "Passenger / Cruise" },
    TANKER: { code: "TANKER", desc: "Tanker" },
};

export const Roles = {
    SYS_SUPER_ADMIN: { code: "SYS_SUPER_ADMIN", desc: "Camel Portal Adminstrator" },
    OFFICER: { code: "OFFICER", desc: "OFFICER" },
    FINANCE: { code: "FINANCE", desc: "SHIPPING LINE FINANCE" },
    OPERATIONS: { code: "OPERATIONS", desc: "SHIPPING LINE DOC VERIFIER" },
    ADMIN: { code: "ADMIN", desc: "ACCOUNT ADMINISTRATOR" }
};

export const AdminOfficerRoles = [Roles.ADMIN.codes];
export const OfficerRoles = [Roles.OFFICER.code];
export const FinanceRoles = [Roles.FINANCE.code];
export const OperationsRoles = [Roles.OPERATIONS.code];


export const CIQM_ACCOUNT_ID = ["GDCE", "CDCD", "GDI", "MMD", "PASQRNT", "PPAPQRNT"];

// KH Ports that have no associated terminal (refer to T_PEDI_MST_PORT_TEMINAL table)
export const KHPortsNoTerminal = ["KHSHP", "KHOKP"];

export const MST_CTRY_URL =
    "/api/co/master/entity/country/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=ctyCode&iColumns=1";
export const MST_ACCN_TYPE_URL = "/api/co/master/entity/accnType";
export const MST_CURRENCY_URL =
    "/api/co/master/entity/currency/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=ccyCode&iColumns=1";
export const MST_CURRENCY = "/api/co/master/entity/currency";
export const MST_BANKS_URL = "/api/co/master/entity/bank";
export const MST_PAYMENT_TYPE_URL = "/api/pedi/mst/paymentType";
export const MST_BANKS_BRANCH_URL = "/api/co/master/entity/bankbr";
export const MST_CONTACT_TYPE_URL = "/api/co/master/entity/contactType";
export const MST_UOM_URL = "/api/co/master/entity/uom";
export const MST_PORT_TYPE_URL = "/api/co/master/entity/portType";
export const MST_PORT = "/api/co/master/entity/port";
export const MST_PORT_KH =
    MST_PORT +
    "/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=portCode&iColumns=1&mDataProp_1=TMstCountry.ctyCode&sSearch_1=KH&mDataProp_2=portStatus&sSearch_2=A";
export const MST_PORT_TERMINAL_BY_PORT =
    "/api/co/pedi/entity/pediMstPortTerminal/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&iColumns=1&mDataProp_0=portTeminalId&mDataProp_2=portTeminalStatus&sSearch_2=A&mDataProp_1=mstPort.portCode&sSearch_1=";
export const MST_PORT_KHP =
    "/api/co/master/entity/port/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=portCode&iColumns=1&mDataProp_1=portCode&sSearch_1=KHP";
export const MST_PORT_MAIN =
    "/api/co/master/entity/port/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=portCode&iColumns=1&mDataProp_1=portDescription&sSearch_1=Autonomous";
export const MST_HS_CODE_TYPE_URL = "/api/co/master/entity/hsCodeType";
export const MST_ADDRESS_TYPE_URL = "/api/co/master/entity/addrType";
export const MST_HSCODE_TYPE_URL = "/api/co/master/entity/hsCodeType";
export const MST_HSCODE_URL = "/api/co/master/entity/hsCode";
export const MST_PROVINCE_URL = "/api/co/pedi/mst/entity/pediMstProvince";
export const MST_DUTY_PAID_AT_URL = "/api/co/pedi/mst/entity/pediMstDutyPaidAt";
export const MST_SHIP_TYPE_URL = "/api/co/pedi/mst/entity/pediMstShipType";
export const MST_THRUSTER_TYPE_URL = "/api/co/pedi/mst/entity/pediMstThrusterType";
export const MST_PAYMENT_TYPE_TABLE_URL = "/api/co/pedi/mst/entity/pediMstPaymentType";
export const MST_RECOVERY_APPLICATION_TABLE_URL = "/api/recoveryApplications";
export const MST_CREW_RANK = "/api/co/pedi/mst/entity/pediMstCrewRank";
export const MST_NUMBER_ENGINE_URL = "/api/co/pedi/mst/entity/pediMstNumberEngine";
export const MST_NUMBER_ENGINE = MST_NUMBER_ENGINE_URL + "/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=neSeq&iColumns=2&mDataProp_1=neStatus&sSearch_1=A";
export const PEDI_MST_ARTICLE_CAT_URL = "/api/co/pedi/mst/entity/pediMstArticleCat";
export const PEDI_MST_ARTICLE_CAT = PEDI_MST_ARTICLE_CAT_URL + "/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=arcCode&mDataProp_1=arcStatus&sSearch_1=A&mDataProp_2=arcCode&iColumns=1";

export const MST_PORT_TERMINAL_URL = "/api/co/pedi/mst/entity/pediMstPortTerminal";
export const MST_DOS_ACTIVITY_URL = "/api/co/pedi/mst/entity/pediMstDosActivity";
export const MST_AREAS_INSPECTED_URL = "/api/co/pedi/mst/entity/pediMstAreasInspected";
export const MST_APP_TYPE_URL = "/api/co/pedi/mst/entity/pediMstAppType";
export const MST_ACTIONS_URL = "/api/co/pedi/mst/entity/pediMstActions";
export const MST_SHIP_BARGE_URL = "/api/co/pedi/mst/entity/pediMstShipBarge";
export const MST_PEDI_NOTIFICATION_PREF_URL = "/api/co/pedi/mst/entity/pediSysNotifyPref";
export const MST_HAZARD_CLASS_URL = "/api/co/pedi/mst/entity/pediMstHazardClass";
export const PEDI_NOTIF_PREF_URL = "/api/pedi/notifications/preferences/template";
export const PEDI_NOTIF_PREF_APPTYPES_URL = "/api/pedi/notifications/preferences/appTypes";
export const PEDI_NOTIF_PREF_ACTIONS_BY_APPTYPES_URL = `/api/pedi/notifications/preferences/actions`;

export const CCM_ACCOUNT_ALL_URL = "/api/co/ccm/entity/accn";
export const CCM_ACCOUNT_ALL_SL_SA = "/api/pedi/manageaccn/slsa";

export const CCM_ACTIVE_ACCOUNT_ALL_URL =
    "/api/co/ccm/entity/accn/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=desc&iSortingCols=1&mDataProp_0=accnId&iColumns=2&mDataProp_1=accnStatus&sSearch_1=A";
export const CCM_ACCOUNT_BY_TYPEID_URL =
    "/api/co/ccm/entity/accn/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=accnName&iColumns=2&mDataProp_1=TMstAccnType.atypId&sSearch_1=";
//&sSearch_1=${acctTypeId}

export const CCM_USER_ALL_URL = "/api/co/ccm/entity/usr";
export const CCM_USER_BY_ACCNID_URL =
    "api/co/ccm/entity/usr/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=usrUid&iColumns=2&mDataProp_1=TCoreAccn.accnId&sSearch_1=";
//&sSearch_1=${accnId}`
export const CCM_USER_APP_CODE_URL =
    "api/co/ccm/entity/usrApp/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=id.uappUid&iColumns=2&mDataProp_1=id.uappUid&sSearch_1=";
export const CCM_GROUP_BY_ACCNID_URL =
    "/api/co/ccm/entity/group/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=id.grpAccnid&iColumns=2&mDataProp_1=id.grpAccnid&sSearch_1=";
//&sSearch_1=${grpAccnid}
export const CCM_APPS_CODE_URL = "/api/co/ccm/entity/apps";
export const CCM_MINISTRY_URL =
    "/api/co/ccm/entity/ministry/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=minCode&iColumns=1";
export const CCM_AGENCY_URL =
    "/api/co/ccm/entity/agency/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=agyCode&iColumns=1";
export const ATTACH_TYPE =
    "/api/co/master/entity/attType/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=mattName&iColumns=1";
export const GET_ATT_TYPE_BY_ID = "/api/co/master/entity/attType/";

export const CAC_ROLE_BY_APP_CODE_URL =
    "api/co/cac/entity/role/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=desc&iSortingCols=1&mDataProp_0=id.roleId&iColumns=2&mDataProp_1=id.roleAppscode&sSearch_1=";
export const CAC_URITYPE_URL = "/api/co/cac/entity/uriType";
export const CAC_PORTAL_URI_URL = "/api/co/cac/portaluri/";
export const CAC_PERMISSION_TYPE_URL = "/api/co/cac/entity/permissionType";
export const CAC_PERMISSIONS_BY_APPCODE_URL =
    "api/co/cac/entity/permission/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=desc&iSortingCols=1&mDataProp_0=permId&iColumns=2&mDataProp_1=appsCode&sSearch_1=";

export const CAN_NOTIFICATION_CHANNEL_TYPE_URL = "/api/co/can/entity/notificationChannel";
export const CAN_NOTIFICATION_CONTENT_TYPE_URL = "/api/co/can/entity/notificationContent";
export const CAN_NOTIFICATION_DEVICE_URL = "/api/co/can/entity/notificationDevice";
export const CAN_NOTIFICATION_TEMPLATE_URL = "/api/co/can/entity/notificationTemplate";

export const ANNOUNCEMENT_TYPE_URL = "/api/co/anncmt/entity/anncmtType";

export const COMMON_SYSPARAM_URL = "/api/co/common/entity/sysparam";
export const COMMON_ATTACH_LIST_BY_REFID_URL =
    "/api/co/common/entity/attach/list?sEcho=3&iDisplayStart=0&iDisplayLength=10&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=attSeq&mDataProp_1=attReferenceid&iColumns=2&sSearch_1=";

export const auditTab = [{ text: "common:recordDetails", icon: <FileCopy /> }];

export const commonTabs = [
    { text: "common:recordDetails", icon: <FileCopy /> },
    { text: "common:properties.title", icon: <Assignment /> },
    { text: "common:audits.title", icon: <Schedule /> },
];

export const registerTabs = [
    { text: "common:register.tabs.details.title", icon: <FileCopy /> },
    { text: "common:register.tabs.admin.title", icon: <Assignment /> },
    { text: "Services", icon: < Build /> },
    { text: "common:register.tabs.suppDocs.title", icon: <Assignment /> },
];

//@deprecated
export const tabList = [
    { text: "Record Details", icon: <FileCopy /> },
    { text: "Properties", icon: <Assignment /> },
    { text: "Audits", icon: <Icon>schedule</Icon> },
];

export const APPROVED_SR_SHIPPINGLINE_URL = "/api/app/sr/sl";
export const APPROVED_SR_VESSELBYSL_URL = "/api/app/sr/vessel/";

export const PEDI_MST_VOYAGE_TYPE_URL = "/api/pedi/mst/voyageTypes";
export const PEDI_MST_SHIP_TYPE_URL =
    "/api/co/pedi/mst/entity/pediMstShipType/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=shipTypeId&iColumns=1&mDataProp_1=shipTypeStatus&sSearch_1=A";
export const PEDI_MST_CARGO_TYPE_URL =
    "/api/co/pedi/mst/entity/pediMstCargoType/list?sEcho=3&iDisplayStart=0&iDisplayLength=1000&iSortCol_0=0&sSortDir_0=asc&iSortingCols=1&mDataProp_0=cargoTypeId&iColumns=1&mDataProp_1=cargoTypeStatus&sSearch_1=A";
export const PEDI_MST_DUTY_PAID_AT_URL = "/api/pedi/mst/dutyPaidAtList";
export const VESSEL_DROP_DOWN = "/api/app/sr/vessel/list/";
export const PEDI_MST_SHIP_TYPE = "/api/co/pedi/mst/entity/pediMstShipType";
export const PEDI_MST_GENDER = "/api/pedi/mst/gender";
export const PEDI_SEC_LEVEL = "/api/pedi/mst/secLevel/";
export const PEDI_MST_CREW_RANK = "/api/pedi/mst/crewRankList";
export const PEDI_MST_THRUSTER_TYPE = "/api/pedi/mst/thrusterType";
export const PEDI_MST_IDENTITY_DOC_TYPE = "/api/pedi/mst/identityDocTypes";
export const MST_PORT_BY_COUNTRY = "/api/pedi/mst/portsByCountry/";
export const MST_CUSS_OFF_TYPE = "/api/pedi/mst/cusOffice";
export const MST_AREAS_INSPECTED = "/api/pedi/mst/areasInspected";
export const MST_APP_FEE_CONFIG = "/api/co/pedi/entity/appFeeConfig";
export const PEDI_MST_BORDER_GATE = "/api/pedi/mst/borderGate";
export const PEDI_MST_EFF_TYPE = "/api/pedi/mst/crewEff";

export const MANIFEST_BILL_NATURE = "/api/pedi/mst/manifest/nature";
export const MANIFEST_BILL_TYPE = "/api/pedi/mst/manifest/type";
export const MANIFEST_MANIFEST_TYPE = "/api/pedi/mst/manifestType";
export const MANIFEST_MANIFEST_TRANSPORT_MODE = "/api/pedi/mst/transportMode";
export const MANIFEST_PACKAGE_TYPE = "/api/pedi/mst/manifest/pkgType";
export const MANIFEST_CONTAINER_TYPE = "/api/pedi/mst/manifest/containerType";
export const MANIFEST_CONTAINER_SIZE = "/api/pedi/mst/manifest/containerSize";

export const MST_DOC_CATEGORY = "/api/co/pedi/mst/entity/pediMstDocCategory";
export const PEDI_ACCN_APPTYPE_ASSOC = "/api/app/report/misc/accn/assoc/"

export const MASTER_CONTAINER_CAT = "/api/pedi/mst/contCat/:contType";
export const MASTER_CONTAINER_CAT_DETAILS = "/api/pedi/mst/contCat/:contType/:contcatCode";
export const MASTER_CONTAINER_TYPE = "/api/pedi/mst/containerType";

export const nilOrManifestOptions = [{ value: "NIL", desc: "NIL" }, { value: "MANIFEST", desc: "As per Manifest" }];

export const PEDI_MANAGE_ACCN_lIST_URL = "/api/pedi/manageaccn/list";

export const MAX_FILE_SIZE = 10; //10M
export const ALLOWED_FILE_EXTS = ['pdf', 'csv', 'xlsx', 'xls', 'doc', 'docx', 'jpeg', 'jpg', 'png', 'ppt'];
export const isCompress = false; // don't compress,
