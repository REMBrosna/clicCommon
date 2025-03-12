import { Box, Grid, IconButton, Paper, Tooltip } from "@material-ui/core";
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import React, { useEffect, useState } from "react";
import C1Information from "app/c1component/C1Information";
import useHttp from "app/c1hooks/http";
import { titleTab, useStyles } from "app/c1utils/styles";
import GetAppIcon from '@material-ui/icons/GetApp';
import { previewPDF, formatDate } from "app/c1utils/utility";



const StyledTableCell = withStyles((theme) => ({
    head: {
        backgroundColor: '#0772BA',
        color: theme.palette.common.white,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const useTableStyle = makeStyles({
    table: {
        minWidth: 450,
    },
    column: {
        width: 50,
    },
});

const JobAuthorisationLetters = ({
    inputData,
    viewType
}) => {

    const [rows, setRows] = useState([]);
    const tableCls = useTableStyle();
    const classes = useStyles();
    const title = titleTab();

    /** ---------------- Declare states ------------------- */
    const { isLoading, res, urlId, sendRequest } = useHttp();

    /** --------------- Update states -------------------- */

    useEffect(() => {
        if (inputData?.jobId !== undefined) {
            let url = `/api/jobs/authLetters/${inputData?.jobId}`;
            console.log("url", url);
            sendRequest(url, "list", "get");
        }

    }, [viewType]);

    useEffect(() => {
        if (!isLoading && res) {
            switch (urlId) {
                case "list": {
                    setRows(res.data);
                    break;
                }
                case "download": {
                    viewFile(res?.data?.attName, res?.data?.attData);
                    break;
                }
            }

        }
    }, [isLoading, res, urlId]);


    /** ---------------- Event handlers ------------------- */
    const handleViewFile = (e, attId) => {
        const url = `/api/jobs/authLetter/download/${attId}`;
        sendRequest(url, "download");
    };

    const viewFile = (fileName, data) => {
        previewPDF(fileName, data);
    };


    return (
        <React.Fragment>
            <Grid container alignItems="flex-start" spacing={1} className={classes.gridContainer}>
                <Grid container item xs={12}>
                    <Box m={1}>
                        <TableContainer component={Paper}>
                            <Table className={tableCls.table} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell align="center">Document ID</StyledTableCell>
                                        <StyledTableCell align="center">Type</StyledTableCell>
                                        <StyledTableCell align="center">Document Name</StyledTableCell>
                                        <StyledTableCell align="center">Date Created</StyledTableCell>
                                        <StyledTableCell align="center"></StyledTableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {rows?.map((el, idx) => {
                                        let row = el;
                                        let parentElement = <TableRow key={idx}>
                                            <TableCell align="center">{row?.attType}</TableCell>
                                            <TableCell align="center">{row?.attName}</TableCell>
                                            <TableCell align="center">{row?.attName}</TableCell>
                                            <TableCell align="center">{formatDate(row?.attDtCreate, true)}</TableCell>
                                            <TableCell align="center">
                                                {row?.attName &&
                                                    <Tooltip title="View">
                                                        <IconButton aria-label="View" type="button"
                                                            color="primary" onClick={(e) => handleViewFile(e, row?.attId)}>
                                                            <GetAppIcon />
                                                        </IconButton>
                                                    </Tooltip>}
                                            </TableCell>
                                        </TableRow>
                                        return <React.Fragment key={idx}>
                                            {parentElement}
                                        </React.Fragment>;
                                    })}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </Grid>



                {/**Information */}
                <Grid container item xs={12}>{/**Space */}</Grid>
                <Grid item lg={12} md={12} xs={12}>
                    <C1Information information="documentDetails" />
                </Grid>
            </Grid>
        </React.Fragment >
    );
};

export default JobAuthorisationLetters;