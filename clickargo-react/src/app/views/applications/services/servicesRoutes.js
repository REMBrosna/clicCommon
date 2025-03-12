import React from "react";

const servicesRoutes = [
    {
        path: "/applications/services",
        component: React.lazy(() => import("../general/SubscribedServices"))
    },
    {
        path: "/applications/jobs/list",
        component: React.lazy(() => import("./jobs/ServiceJobsList"))
    },
    {
        path: "/applications/jobs/:viewType/:jobId",
        component: React.lazy(() => import("./jobs/ServiceJobFormDetails.jsx"))
    },
    {
        path: "/applications/bl",
        component: React.lazy(() => import("./deliveryOrders/ServiceDeliveryOrdersList"))
    },

];

export default servicesRoutes;

