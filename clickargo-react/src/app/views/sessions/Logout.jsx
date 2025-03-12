import { useEffect } from "react";
import useAuth from "app/hooks/useAuth";
import history from "history.js";

const Logout = () => {
    const { logout } = useAuth();

    useEffect(() => {
        try {
            logout();
            history.push("/session/login");

            localStorage.removeItem("loginPath");
        } catch (e) {
            console.log(e);
        }
    }, []);

    return null;
}
export default Logout;
