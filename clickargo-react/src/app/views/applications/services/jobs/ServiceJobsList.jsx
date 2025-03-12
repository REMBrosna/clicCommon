import React from "react";
import C1DataTable from "app/c1component/C1DataTable";
import C1DataTableActions from "app/c1component/C1DataTableActions";
import C1ListPanel from "app/c1component/C1ListPanel";
import { jobsDb, MOCK_JOBS_STATUS } from "fake-db/db/jobs";
import C1Information from "app/c1component/C1Information";
import { customFilterDateDisplay, formatDate } from "app/c1utils/utility";

import { useStyles } from "app/c1utils/styles";


const ServiceJobsList = () => {

    const classes = useStyles();
    const columns = [
        {
            name: "jobId",
            label: "Job ID"
        },
        {
            name: "blNo",
            label: "BL Number"
        },
        {
            name: "type",
            label: "Type"
        },
        {
            name: "authorizedParty.accnName",
            label: "Authorized Party"
        },
        {
            name: "dtSubmitted",
            label: "Date Submitted",
            options: {
                filter: true,
                // filterType: 'custom',
                display: true,
                filterOptions: {
                    display: customFilterDateDisplay
                },
                customBodyRender: (value, tableMeta, updateValue) => {
                    return formatDate(value, true);

                }
            }
        },
        {
            name: "status",
            label: "Status"
        },
        {
            name: "action",
            label: " ",
            options: {
                filter: false,
                display: true,
                viewColumns: false,
                customBodyRender: (value, tableMeta, updateValue) => {
                    const status = tableMeta.rowData[5];
                    const jobId = tableMeta.rowData[0];
                    return <C1DataTableActions
                        editPath={status === MOCK_JOBS_STATUS.NEW.code ? `/applications/jobs/edit/${jobId}` : null}
                        viewPath={`/applications/jobs/view/${jobId}`}
                        removeEventHandler={status === MOCK_JOBS_STATUS.NEW.code ? () => console.log("remove item") : null} />
                }
            }
        }
    ]

    return (<React.Fragment>
        <C1ListPanel
            routeSegments={[
                { name: "ClicDO Jobs" }
            ]} information={<C1Information information="jobsListing" />}>

            <C1DataTable
                isServer={false}
                columns={columns}
                dbName={jobsDb}
                title="ClickDO Jobs"
                defaultOrder="jobId"
                defaultOrderDirection="desc"
                isShowToolbar
                isShowFilterChip
                isShowDownload={false}
                isShowPrint={false}
                isRowSelectable={false}
                showAdd={{
                    type: "redirect",
                    path: "/applications/jobs/new/0"
                }}
            />
        </C1ListPanel >


    </React.Fragment>);
};

export default ServiceJobsList;