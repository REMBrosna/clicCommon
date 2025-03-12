import React from "react";
import JWTLogin from "./login/JwtLogin";
import NotFound from "./NotFound";
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./ResetPassword";
import ForceChangePassword from "./ForceChangePassword";
import JWTRegister from "./register/JwtRegister";
import SessionTimeout from "./SessionTimeout";
import NoPermission from "./NoPermission";
import Contact from "./info/Contact";
import FAQ from "./info/FAQ";

import PreviewFAQ from "./info/PreviewFAQ";
import CertificateCriteriaDetail from "./certificate/CertificateCriteriaDetail";
import Announcement from "./notification/Announcement";
import JwtLoginCustom from "./login/JwtLoginCustom";
import Logout from "./Logout";

const sessionRoutes = [
    {
        path: "/session/signup",
        component: JWTRegister,
    },
    {
        path: "/session/register/:regId",
        component: JWTRegister,
    },
    {
        path: "/session/signin",
        component: JWTLogin,
    },
    {
        path: "/session/notification",
        component: Announcement,
    },
    {
        path: "/session/forgot-password",
        component: ForgotPassword,
    },
    {
        path: "/session/reset-password",
        component: ResetPassword,
    },
    {
        path: "/session/force-change-password",
        component: ForceChangePassword,
    },
    {
        path: "/session/contact",
        component: Contact,
    },
    {
        path: "/session/faq",
        component: FAQ,
    },


    {
        path: "/session/assets/pdf/:fileName",
        component: PreviewFAQ,
    },
    {
        path: "/session/certificate/:type/:certToken",
        component: CertificateCriteriaDetail,
    },
    {
        path: "/certificate/inquiry",
        component: CertificateCriteriaDetail,
    },
    {
        path: "/session/404",
        component: NotFound,
    },
    {
        path: "/session/timeout",
        component: SessionTimeout,
    },
    {
        path: "/session/nopermission",
        component: NoPermission,
    },
    {
        path: "/activate/:token",
        component: React.lazy(() =>
            import("./register/RegActivate")
        )
    },
    {
        path: "/:name/login",
        component: JwtLoginCustom,
    },
    {
        path: "/session/logout",
        component: Logout,
    },
];

export default sessionRoutes;
