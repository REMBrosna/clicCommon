import React from "react";
import { Redirect } from "react-router-dom";
import landingRoutes from "./views/applications/general/generalRoutes";


const redirectRoute = [
    {
        path: "/",
        exact: true,
        component: () => <Redirect to="/subscription" />
    },
];

const errorRoute = [
    {
        component: () => { console.log("Fail to find URL", window.location.href); return <Redirect to="/session/404" /> },
    },
];

const routes = [
    ...redirectRoute,
    ...landingRoutes,
    ...errorRoute,
];

export default routes;
