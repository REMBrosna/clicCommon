import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import React from 'react';
import { useHistory } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function ServicesCard({
    docObj,
    toggleEventHandler,
    handleClickStatus
}) {

    console.log("docObj: ", docObj);
    const useStyles = makeStyles({
        root: {
            width: 300,
            height: 300,
            boxShadow: "0px 10px 15px 0px rgba(0,0,0,0.4)",
            "-webkit-box-shadow": "0px 10px 15px 0px rgba(0,0,0,0.4)",
            borderRadius: '1.5rem',
            fontFamily: 'Poppins',
            transition: docObj.isSubscribed ? "transform 300ms ease-in-out" : "",
            backgroundColor: docObj.isSubscribed ? '#13b1ed' : '#B4BABD',
            "&:hover": docObj.isSubscribed ? {
                transform: "scale(1.1)",
                transition: "transform 300ms ease-in-out",
            } : {}
        },
        actionArea: {
            textAlign: 'center',
            pointerEvents: !docObj.isSubscribed ? 'none' : ''
        },
        progressLabel: {
            position: "absolute",
            width: "100%",
            height: "100%",
            zIndex: 1,
            maxHeight: "20px", // borderlinearprogress root.height
            textAlign: "center",
            display: "flex",
            alignItems: "center",
            "& span": {
                width: "100%"
            }
        },
        media: {
            height: 240,
            padding: '10px 10px',
            margin: 'auto',
            filter: !docObj.isSubscribed ? 'grayscale(100%)' : ''
        },
        cardHeader: {
            color: '#1a90ff',
            fontSize: '100px'
        },
        statusList: {
            fontWeight: 600,
            fontSize: 20,
            fontFamily: 'Poppins'
            // '&:hover': {
            //     backgroundColor: grey[300]
            // },
        },
        cardContent: {
            position: 'absolute',
            bottom: 0
        },
        title: {
            marginBottom: 50,
            color: '#fff'
        }
    });

    const { user } = useAuth();
    const classes = useStyles();
    const history = useHistory();

    const routeChange = (appUrl) => {
        //TODO CHANGE TO POST
        // let map = new Set(user.authorities.map((el) => el.authority));
        // const isOfficer = map.has(Roles.OFFICER.code)
        // let path = `http://localhost:8888/clicdo/session/sso?token=${window.localStorage.getItem("accessToken")}`;
        //let path = `${process.env.PUBLIC_URL}/${appUrl}`; http://ct2-uat.vcargocloud.com/clickargo/clicdo

        let path = `/${appUrl}`;
        if (appUrl === 'clicdo' && process.env.REACT_APP_CLICDO_URL !== 'clicdo') {
            path = `${process.env.REACT_APP_CLICDO_URL}/session/sso?token=${window.localStorage.getItem("accessToken")}`;
        }

        window.location.href = path;

        // windowOpenInPost("http://localhost:8888/clicdo/session/sso", ["token"], [window.localStorage.getItem("accessToken")])
    }
    return (
        <Card className={classes.root} elevation={0}>
            <CardActionArea onClick={() => routeChange(docObj?.svctId?.toLowerCase())} className={classes.actionArea}>
                <CardContent>
                    <img src={`${process.env.PUBLIC_URL}/assets/images/services/${docObj.svctId}.png`} className={classes.media} alt="logo" />
                    <Typography color="textPrimary" component="div" className={classes.title}>
                        <span className={classes.statusList}>{docObj.svctName}</span>
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}