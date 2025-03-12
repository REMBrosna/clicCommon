import React, { useEffect, useState } from "react";
import Grid from "@material-ui/core/Grid";
import ServicesCard from "app/clicdocomponent/ServicesCard";
import { useStyles } from "app/c1utils/styles";
import C1Information from "app/c1component/C1Information";
import useHttp from "app/c1hooks/http";
import { MatxLoading } from "matx";
import { useMediaQuery, useTheme } from "@material-ui/core";
import { isArrayNotEmpty } from "app/c1utils/utility";
import RedirecMessage from "app/atomics/molecules/RedirecMessage";

const SubscribedServices = () => {

    const classes = useStyles();
    const theme = useTheme();
    const isSm = useMediaQuery(theme.breakpoints.up('sm'));
    const serviceMaps = [
        { id: "ClicDO", title: "ClicDO", img: "clicDO.png", url: "clicdo" },
        { id: "ClicTruck", title: "ClicTruck", img: "clicTruck.png", url: "clictruck" },
        { id: "ClicDeclare", title: "ClicDeclare", img: "clicDeclare.png", url: "clicdeclare" },
        { id: "ClicGatePass", title: "ClicGatePass", img: "clicGatePass.png", url: "clicgatepass" },
        { id: "ClicDepot", title: "ClicDepot", img: "clicDepo.png", url: "clicdepot" },
    ]

    const [loading, setLoading] = useState(true);
    const [subedSvc, setSubedSvc] = useState([]);
    const [soleService, setSoleService] = useState({ service: null, path: null });
    const { isLoading, isFormSubmission, res, validation, error, urlId, sendRequest } = useHttp();

    const setSubedSvcWrap = (subedService) => {
        console.log("subedService: ", subedService);
        if (subedService && subedService.length > 0) {
            setSubedSvc(subedService);
        }
    }

    useEffect(() => {
        sendRequest("/api/v1/clickargo/svc/subscriptions", "subscriptions", "get");
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {
        //iterate through the sub services and check if there's only one isSubscribed
        if (isArrayNotEmpty(subedSvc)) {
            const count = subedSvc.filter(el => {
                return el?.isSubscribed == true;
            }).length;

            //only count = 1 will redirect
            if (count == 1) {
                //get the id of the only service subscription
                const service = subedSvc.find(el => {
                    return el?.isSubscribed == true;
                });

                const loc = service?.svctId?.toLowerCase();
                const serviceName = service?.svctName;
                setSoleService({ service: serviceName, path: `/${loc}` });


            }
        }
    }, [subedSvc])

    useEffect(() => {

        if (!isLoading && !error && res && !validation) {
            setLoading(isLoading);
            switch (urlId) {
                case "subscriptions": {
                    setSubedSvcWrap(res?.data);
                    break;
                }
                default:
                    break;
            }
        }
    }, [urlId, isLoading, isFormSubmission, res, validation, error]);

    return (loading ? <MatxLoading /> : (
        <React.Fragment>
            {soleService.service && soleService.path && <RedirecMessage service={soleService.service} timer={3} path={soleService.path} />}
            <Grid container className={classes.gridContainer} justifyContent="center" style={{ marginTop: 40, marginBottom: 40 }}>
                <Grid item lg={8} xs={12} >
                    <Grid container spacing={isSm ? 5 : 2} justifyContent={isSm ? 'flex-start' : 'center'} style={{ marginLeft: isSm ? '5.6vw' : '' }}>
                        {subedSvc && subedSvc.map((svc, idx) => {
                            let displayEl = null;
                            if (svc && svc.id !== '') {
                                displayEl = <ServicesCard docObj={svc} key={svc.id}></ServicesCard>
                            }

                            return <Grid item key={idx}>
                                {displayEl}
                            </Grid>
                        })}
                    </Grid>
                </Grid>
            </Grid>
        </React.Fragment >
    ));
};

export default SubscribedServices;