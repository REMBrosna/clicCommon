import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import React from "react";

import C1TextArea from 'app/c1component/C1TextArea';

import C1OutlinedDiv from "./C1OutlinedDiv";

const useStyles = makeStyles(({ palette, ...theme }) => ({

    card: {
        width: "1200!important",
        borderRadius: 12,
        margin: "5rem",
    },

    root: {
        backgroundColor: '#0772BA',
        borderColor: palette.divider,
        display: "table",
        height: "var(--topbar-height)",
        borderBottom: "1px solid transparent",
        paddingTop: "1rem",
        paddingBottom: "1rem",
        zIndex: 98,
        paddingLeft: "1.75rem",
        [theme.breakpoints.down("sm")]: {
            paddingLeft: "1rem",
        },
    },

    brandText: {
        color: palette.primary.contrastText,
    },

}));


const C1Information = ({ information }) => {

    const getInformation = (info) => {

        if (info === "companyDetails" || info === "userDetails")
            return "*This administrator will be emailed with login credentials once the company account has been approved by Clickargo system administrators. If there are queries associated to the registration process, the registered administrator will be notified either via mobile or email.";
        else if (info === "serviceDetails")
            return "*Click on the toggle to indicate your company's interest in subscribing any of the Clic's services."
        else if (info === 'documentDetails')
            return "*Please upload all mandatory documents in PDF format to facilitate the registration process. These documents will be reviewed by the system administrators and if there are queries, the administrator specified in the previous tab will be contacted for resolution.";
        else if (info === 'accnProfileCompanyDetails') {
            return "*Please update your company details here. There are fields that are locked which cannot be updated."
        } else if (info === 'accnProfileSuppDocs') {
            return "*To update any documents in this list, please email the updated document to support@clickargo.com indicating your account id (in the company details tab) and the document type that you are updating. A verification and approval process will be conducted in the backoffice. Once the document is approved, it will be reflected in this list.";
        } else if (info === 'accnDetailsProfile' || info === 'accnDetailsServices' || info === 'accnDetailsCreditLine') {
            return "*Please update all information to ensure correct information about the account is captured. If there are details that needs clarifications, please contact the representative of the company to obtain the information. Supporting documents required for the account registration needs to be added to the document list. When all data is updated properly, click on the Verified button. The Submit button will be enabled once all tabs are verified. All details will be submitted to the management for approval.";
        } else if (info === "jobsListing") {
            return "*To view any of your jobs click on the EYE button. To edit any draft jobs, click on the PENCIL button. To delete any draft jobs, click on the TRASH BIN button."
        } else if (info === "jobDetails") {
            return "*Update all the information to ensure correct information about the job is captured. Please check that the correct bl document file has been uploaded for a seamless process."
        } else if (info === 'onboarding') {
            return "*All accounts are registered in the list above. Online registrations are automatically added into this list. Use the Add function to add a new account manually. The View function is present for ACTIVE records that are approved by the management. The Edit function is to be used for editing NEW record for submission to management for approval or to update ACTIVE record. Once ACTIVE is changed, it is be updated to UPDATED before it becomes VERIFIED and PENDING_APPROVAL.";
        } else if (info === 'manageUserListing') {
            return "*To view any of your company user's details without editing, click on the EYE button. " +
                "To edit user's profile or update their roles, click on the PENCIL button. " +
                "To suspend the user use the UNLINK button. To reinstate a suspended user, use the CHAIN button."
        } else if (info === 'manageUserDetails') {
            return "*Please update the user details into the fields provided in the form. " +
                "Use the switch to enable/disable notification on the channel of SMS, Email or Telegram. " +
                "Note that the login id will be of the form ACCOUNTID_USERID."
        } else if (info === 'manageUserRoles') {
            return "*To add or remove roles for the user, simply drag them between the Available Roles and the Selected Roles group. " +
                "A user can have more than a singular role, but must have at least one role"
        } else if (info === 'authorisationsListing') {
            return "*To view any of your company authorized forwarders without editing, click on the EYE button. " +
                "To terminate the authorization use the UNLINK button. " +
                "To download the authorization letter in PDF format, click on the DOWNLOAD button."
        } else if (info === 'authorisationsFormDetails') {
            return "*Please update all the information to ensure correct information about the account is captured. " +
                "If there are details that needs clarifications, please contact the representative of the company to secure the information. " +
                "Supporting documents required for the account registration needs to be added to the document list. " +
                "When all data is updated properly, click on the Verified button. The Submit button will be enabled once all tabs are verified. " +
                "All details will be submitted to the management for approval. " +
                "Authoriser and Authorised party applicant must be of managerial position."
        } else if (info === 'auditInfo') {
            return "*The audited event associated with the user is presented here. " +
                "Filtering is supported to sniff out audit records by criteria(s)."
        } else if (info === 'bol') {
            return "*To view any of the Bill of Ladings, click on the EYE button. " +
                "To reject any Bill of Ladings, click on the REJECT button."
        } else if (info === 'doClaimJobs') {
            return "*To view any of your jobs without editing, click on the EYE button. " +
                "To edit any draft jobs, click on the PENCIL button. " +
                "To delete any draft jobs, click on the TRASH BIN button."
        } else if (info === 'claimJobAttachments') {
            return "*Documents associated with all the BL is captured in this list."
        } else if (info === 'claimJobQueries') {
            return "*Ensure that there are no outstanding queries by the Shipping Line. " +
                "Document can be manually or temporarily added when there is an outstanding query.";
        } else if (info === 'ShipLineDoClaimJobs') {
            return "*Click on the 'document' icon to perform document verification. " +
                "Click on the 'money' icon to perform payment verification."
        } else if (info === 'shiplineDoClaimJobs') {
            return "*The charges amount will be shown once the job is submitted and verified. " +
                "The profoma invoices are also available for download when the job details and documents are verified. " +
                "Once payment is made, DO claim tasks can be started to claim the DO under BLs."
        } else if (info === 'shiplineClaimJobQueries') {
            return "*Create a query to inform the responder on the changes or correct documents required on what has been claimed. ";
        } else if (info === 'doClaimJobDetailsDrf') {
            return "* Please update all the information to ensure correct information about the job is captured. Please note that once the job has been confirmed, BLs cannot be edited or removed from the list."
        } else if (info === 'doClaimJobDetailsNew') {
            return "*The charge amount will be shown once the job is submitted and verified. The profoma invoices are also available for download when the job details and documents are verified. Once payment is made, DO claim tasks can be started to claim the DO under the BLs."
        } else if (info === 'doClaimJobDetailsSub') {
            return "*Once the payment is made, DO claim tasks can be started to claim the DO under the BLs."
        } else if (info === 'doClaimJobDetailsVer') {
            return "*Once the payment is verified, DO claim tasks can be started to claim the DO under the BLs.";
        } else if (info === 'doClaimJobDetailsPayVer') {
            return "*Tasks can be started to claim the DO(s) associated with the BL in the list.";
        } else if (info === 'doClaimTaskDetails') {
            return "*Once the list of DO are confirmed, click on the Submit button to retrieve the DO documents from the shipping line."

        } else if (info === 'auditDetails') {
            return "*The audited event associated with the job is presented here. Filtering is supported to sniff out audit records by criterium/criteria.";
        } else if (info === 'doJobPayments') {
            return '*Once submitted task has been submitted, the DOs listed within the tasks cannot be updated or removed. The shipping line will proceed to review the letter of authorisation documents, the DO charges will only be shown when the documents have been verified. The DO document and Invoice will only be made available for download once payment has been made and verified..'
        }
    };

    return (
        <React.Fragment>
            <C1OutlinedDiv label="Information">
                {getInformation(information)}
            </C1OutlinedDiv>
        </React.Fragment >

    );
};

export default C1Information;