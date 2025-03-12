import React from "react";
import C1DataTable from 'app/c1component/C1DataTable';
import moment from "moment";
import PropTypes from 'prop-types';
import { useTranslation } from "react-i18next";
import { Box } from "@material-ui/core";
import C1TextArea from "app/c1component/C1TextArea";
import Grid from "@material-ui/core/Grid";

/**
 * @param filterId - id of the record to display the audits
 */
const C1AuditTab = ({ filterId }) => {
    const { t } = useTranslation(["common"]);
    // let snackBar = null;
    const columns = [
        {
            name: "audtId", // field name in the row object
            label: "Audit ID", // column title that will be shown in table
            options: {
                display: false,

            }
        },
        {
            name: "audtEvent", // field name in the row object
            label: t("audits.table.headers.event"), // column title that will be shown in table
            options: {
                sort: true,
                filter: true,
            },

        },
        {
            name: "audtTimestamp",
            label: t("audits.table.headers.timestamp"),
            options: {
                sort: true,
                customBodyRender: (value, tableMeta, updateValue) => {
                    return moment(value).format('YYYY-MM-DD HH:mm:ss');
                },
            },
        },
        {
            name: "audtRemarks",
            label: t("audits.table.headers.remarks"),

        },
        {
            name: "audtUid",
            label: t("audits.table.headers.usrUid"),

        },
        {
            name: "audtUname",
            label: t("audits.table.headers.usrName"),

        },
        {
            name: "audtReckey",
            label: "Audit Key",
            options: {
                display: false,
                filter: false,
                filterType: 'custom',
                filterList: [filterId === undefined || filterId === null ? null : filterId],

            }

        },
        {
            name: "audtParam1",
            label: "Audit Param",
            options: {
                display: false,
                filter: false
            }
        },
    ];

    return (
        <React.Fragment>
            <Box className="p-3">
                <C1DataTable url="/api/co/common/entity/auditLog"
                    columns={columns}
                    isShowToolbar={false}
                    defaultOrder="audtTimestamp" isRowsSelectable={false}
                    defaultOrderDirection="desc" isShowFilterChip={false} />
            </Box>

            <Box mt={2} marginLeft={5} marginRight={5}>
                <Grid container spacing={3} alignItems="center">
                    <Grid container item xs={12} sm={12}>
                        <C1TextArea
                            label={"Information"}
                            name="information"
                            disabled={true}
                            required={false}
                            // textLimit={1024}
                            value={"*The audited event associated with the user is presented here. " +
                                "Filtering is supported to sniff out audit records by criteria(s)."
                            }
                            // onChange={handleInputChange}
                            // error={errors && errors["pediAppDecHdr.vdecPetroleumUseInVessel"] ? true : false}
                            // helperText={errors && errors["pediAppDecHdr.vdecPetroleumUseInVessel"] ? errors["pediAppDecHdr.vdecPetroleumUseInVessel"] : null}
                            // changes={isDisabled ? changes["pediAppDecHdr.vdecPetroleumUseInVessel"] : null}
                        />
                    </Grid>
                </Grid>
            </Box>

        </React.Fragment>
    );
};


C1AuditTab.propTypes = {
    filterId: PropTypes.string
}

export default C1AuditTab;