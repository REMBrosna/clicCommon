import React from "react";

const generalRoutes = [
    {
        path: "/subscription",
        component: React.lazy(() => import("./SubscribedServices"))
    },
    {
        path: "/payments",
        component: React.lazy(() => import("./PaymentsList"))
    },
    {
        path: "/help",
        component: React.lazy(() => import("./Help"))
    },

];

export default generalRoutes;
