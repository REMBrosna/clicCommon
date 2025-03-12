import { AppBar, Avatar, Button, Card, Checkbox, CircularProgress, CssBaseline, FormControlLabel, Grid, InputAdornment, Paper, Toolbar, Tooltip, Typography } from "@material-ui/core";
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from "@material-ui/core/styles";
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import CloseIcon from '@material-ui/icons/Close';
import clsx from "clsx";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { TextValidator, ValidatorForm } from "react-material-ui-form-validator";

import useHttp from "app/c1hooks/http";
import useAuth from 'app/hooks/useAuth';
import LanguageSelector from "app/MatxLayout/SharedCompoents/LanguageSelector";
import history from "history.js";

import Announcement from "../notification/Announcement";

import notificationImage from './NotificationBell.PNG';
import { LockOutlined, Visibility, VisibilityOff } from "@material-ui/icons";

import ClickargoLoginBack from "./ClickargoLoginBack.png";
import { Link } from "react-router-dom";



const useStyles = makeStyles((theme) => ({
    root: {
        height: '100vh',
    },
    image: {
        backgroundImage: `url(${ClickargoLoginBack})`,
        backgroundRepeat: 'no-repeat',
        backgroundColor:
            theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
        backgroundSize: 'cover',
    },
    paper: {
        margin: theme.spacing(10, 4),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
        color: '#FFFFFF'
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        backgroundColor: '#13B1ED',
        padding: "14px 14px"
    },
    login: {
        backgroundColor: '#0772BA'
    },
    links: {
        color: '#FFFFFF',
    }

}));

let LoginStatus = {};

const LoginStatusArray = [
    "AUTHORIZED_REG",
    "AUTHORIZED_IGNORE",
    "UNAUTHENTICATED",
    "UNAUTHORIZED",
    "AUTHORIZED_LOGIN",
    "AUTHORIZED_LOGIN_CHNG_PW_RQD",
    "ACCOUNT_SUSPENDED",
    "ACCOUNT_INACTIVE",
    "USER_SESSION_EXISTING",
    "PASSWORD_RESET_SUCCESS",
    "SESSION_EXPIRED_OR_INACTIVE",
];

LoginStatusArray.map((status) => LoginStatus = { ...LoginStatus, [status]: status });

const JwtLogin = (props) => {
    const { t } = useTranslation(['session']);

    const { state } = props?.location;
    const { isLoading, isFormSubmission, res, validation, error, urlId, sendRequest } = useHttp();

    const [data, setData] = useState([]);

    const [open, setOpen] = React.useState(false);
    const [loading, setLoading] = useState(false);
    const [userInfo, setUserInfo] = useState({ email: "", password: "", isRememberMe: false });
    const [message, setMessage] = useState('');
    const { login } = useAuth();
    //const success = useSelector(state => state.success);
    const classes = useStyles();


    const [openModal, setOpenModal] = useState(false);

    useEffect(() => {
        setLoading(false);
        // sendRequest("/api/pedi/announcement/public/CPEDI", "getAnnouncement", "get", {});
        // eslint-disable-next-line
    }, []);

    useEffect(() => {
        if (!isLoading && !error && res) {
            if (urlId === 'getAnnouncement') {
                setData(res.data);
                if (res.data?.length > 0) {
                    setOpen(true);
                }
            }
        } else if (error) {
            setLoading(false);
        }
        // eslint-disable-next-line
    }, [urlId, isLoading, isFormSubmission, res, validation, error]);

    const handleChange = ({ target: { name, value } }) => {
        let temp = { ...userInfo };
        if (name === 'email') {
            // username to uppercase
            temp[name] = value.toUpperCase();
        } else {
            temp[name] = value;
        }
        setUserInfo(temp);
    };

    //NOTE: Use below for backend api
    const handleFormSubmit = async (event) => {
        setLoading(true);
        try {
            let data = await login(userInfo.email, userInfo.password, userInfo.isRememberMe);

            switch (data.loginStatus) {
                case LoginStatus.AUTHORIZED_LOGIN: {
                    console.log("state", state);
                    if (state && state.redirectUrl)
                        history.push(state.redirectUrl);
                    else
                        history.push("/");

                    break;
                }
                case LoginStatus.AUTHORIZED_LOGIN_CHNG_PW_RQD:
                    history.push("/session/force-change-password", userInfo.email);
                    break;
                default:
                    setMessage(data.err);
                    setLoading(false);
                    break;
            }
        } catch (e) {
            console.log(e);
            setMessage(e.message);
            setLoading(false);
        }
    };

    const handleChangePassword = () => {
        history.push({
            pathname: "/session/forgot-password"
        });
    }

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpen(false);
    };

    const openNotificationModal = () => {
        setOpenModal(true);
        setOpen(false);
    }
    const handleAnnouncementClose = (cal) => {
        setOpenModal(false);
        setOpen(true);
    }

    const [togglePassword, setTogglePassword] = useState(false);

    const handleTogglePasswordHide = () => {
        setTogglePassword(!togglePassword);
    }


    return (
        <React.Fragment>
            <Grid container component="main" className={classes.root}>
                <CssBaseline />
                <Grid item xs={12} sm={8} md={9} className={classes.image} />
                <Grid item xs={12} sm={4} md={3} component={Paper} elevation={6} className={classes.login}>
                    <LanguageSelector showText={true} />
                    <Grid item className={classes.paper}>
                        <Avatar className={classes.avatar}>
                            <LockOutlined />
                        </Avatar>
                        <Typography component="h1" variant="h5" style={{ color: '#fff' }}>
                             {t("login.btn.signIn")}
                        </Typography>
                        <ValidatorForm className={classes.form} onSubmit={handleFormSubmit}>
                            {t("login.label.userId")}
                            <TextValidator
                                className="mb-6 w-full"
                                variant="outlined"
                                size="medium"
                                onChange={handleChange}
                                type="text"
                                name="email"
                                value={userInfo.email}
                                validators={["required"]}
                                errorMessages={[t("login.msgs.error.usrIdRequired")]}
                                style={{ backgroundColor: '#fff' }} />
                            {t("login.label.pwd")}
                            <TextValidator
                                className="mb-6 w-full"
                                variant="outlined"
                                size="medium"
                                onChange={handleChange}
                                name="password"
                                type={togglePassword ? "text" : "password"}
                                value={userInfo.password}
                                validators={["required"]}
                                errorMessages={[t("login.msgs.error.pwdRequired")]}
                                InputProps={{
                                    endAdornment: <InputAdornment position="end" required>
                                        <IconButton
                                            aria-label="Show Password"
                                            onClick={handleTogglePasswordHide}>
                                            {togglePassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                }}
                                style={{ backgroundColor: '#fff' }} />
                            {message && <p className="text-error">{message}</p>}
                            <Grid container>
                                <Grid item xs>
                                    <FormControlLabel
                                        //className="mb-2 min-w-288"
                                        name="isRememberMe"
                                        onChange={handleChange}
                                        control={
                                            <Checkbox size="small" onChange={({ target: { checked } }) =>
                                                handleChange({
                                                    target: { name: "isRememberMe", value: checked },
                                                })
                                            } checked={userInfo.isRememberMe || false} />
                                        } label={t("login.label.rememberMe")} />

                                </Grid>
                            </Grid>
                            <Grid className="relative">
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    color="primary"
                                    disabled={loading}
                                    className={classes.submit}>
                                    {t("login.btn.signIn")}
                                </Button>
                                {loading && (<CircularProgress size={24} style={{
                                    position: "absolute",
                                    top: "50%",
                                    left: "50%",
                                    marginTop: -12,
                                    marginLeft: -12
                                }} />)}
                            </Grid>
                            <Grid container justifyContent="flex-end">
                                <Grid item>
                                    <Grid item xs style={{ paddingTop: '10px' }}>
                                        <Link to="/session/forgot-password" variant="body2" className={classes.links}>
                                            {t("login.label.forgotPwd")}
                                        </Link>
                                    </Grid>
                                </Grid>
                            </Grid>
                            {/* <Grid container>
                                <Grid item>
                                    <Link href="/session/signup" variant="body2" className={classes.links}>
                                        {t("login.label.noAccount")} {t("login.btn.signUp")}
                                    </Link>
                                </Grid>
                            </Grid> */}
                        </ValidatorForm>
                    </Grid>
                </Grid>
            </Grid>

            <div>

                <Announcement onOpen={{ open: openModal }} onClose={handleAnnouncementClose} data={data} />
                <Snackbar
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    open={open}
                    onClose={handleClose}
                    message={
                        "Message"
                    }>
                    <div style={notificationStyle}>
                        <div style={{ float: "right" }}>
                            <React.Fragment>
                                <IconButton size="small" aria-label="close" color="inherit" onClick={handleClose}>
                                    <CloseIcon fontSize="small" />
                                </IconButton>
                            </React.Fragment>
                        </div>
                        <div style={{ float: "left" }}>
                            <img src={notificationImage} alt="notification" style={{ width: "70px" }} />
                        </div>
                        <div style={{ padding: "5px 0px 0px 5px" }}>
                            <h6>{data[0]?.canuSubject.length > 50 ? data[0]?.canuSubject.substring(0, 50) + "..." : data[0]?.canuSubject}</h6>
                            <span>{data[0]?.canuDescription.length > 23 ? data[0]?.canuDescription.substring(0, 30) + "..." : data[0]?.canuDescription}</span>
                            <div style={{ float: "right", paddingBottom: "3px", cursor: "pointer", paddingTop: "0px" }} onClick={openNotificationModal}>
                                <ChevronRightIcon style={{ boxSizing: "unset" }} />
                            </div>
                        </div>
                    </div>
                </Snackbar>
            </div>

        </React.Fragment >
    );
};

const notificationStyle = {
    backgroundColor: 'white',
    marginLeft: "2px",
    width: "350px",
    height: "90px",
    padding: "10px",
    borderRadius: '5px',
    // cursor: "pointer"
}
export default JwtLogin;
