import React, { useEffect } from "react";
import useAuth from 'app/hooks/useAuth';
import { AccountTypes, Roles, OfficerRoles } from "app/c1utils/const";

import history from "history.js";

const Home = () => {
    const { user } = useAuth();

    useEffect(() => {
        let authorities = user.authorities;
        if (authorities.some(el => OfficerRoles.includes(el.authority))) {
            if (user?.coreAccn?.TMstAccnType?.atypId === AccountTypes.ACC_TYPE_SL.code) {
                //land on shipping line
                history.push("/applications/services/ff/workbench");
            } else if (user?.coreAccn?.TMstAccnType?.atypId === AccountTypes.ACC_TYPE_CO.code) {
                //land on cargo owner
                history.push("/applications/services/co/list")
            } else if (user?.coreAccn?.TMstAccnType?.atypId === AccountTypes.ACC_TYPE_FF.code) {
                //land on freight forwarder
            }
        } else if (authorities.some(el => el.authority === Roles.ADMIN.code)) {
            //land on account admin
        } else if (authorities.some(el => el.authority === Roles.SYS_SUPER_ADMIN.code)) {
            //land on system administrator
        }
    }, [user])

    return (<React.Fragment>

    </React.Fragment>

    );
};

export default Home;