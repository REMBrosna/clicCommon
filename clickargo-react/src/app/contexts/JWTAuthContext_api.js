import React, { createContext, useEffect, useReducer } from "react";
import jwtDecode from "jwt-decode";
import axios from "axios.js";
import { MatxLoading } from "matx";
import { encodeString } from "app/c1utils/utility";
// import { getDynamicNavigationByUser } from "app/redux/actions/NavigationAction";

const initialState = {
  isAuthenticated: false,
  isInitialised: false,
  user: null,
  token: null,
  profile: null
};

const isValidToken = (accessToken) => {
  if (!accessToken) {
    return false;
  }

  const decodedToken = jwtDecode(accessToken);
  // console.log(decodedToken.exp, Date.now(), (decodedToken.exp < Date.now()));
  return decodedToken.exp > Date.now();
};

const setSession = (accessToken) => {

  if (accessToken) {
    localStorage.setItem("accessToken", accessToken);
    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
  } else {
    localStorage.removeItem("accessToken");
    delete axios.defaults.headers.common.Authorization;
  }
};

const setProfile = (profile) => {
  if (profile) {
    localStorage.setItem("profile", JSON.stringify(profile));
  }
}

const reducer = (state, action) => {
  switch (action.type) {
    case "INIT": {
      const { isAuthenticated, user } = action.payload;

      return {
        ...state,
        isAuthenticated,
        isInitialised: true,
        user,
      };
    }
    case "LOGIN": {
      const { user } = action.payload;

      const { userId } = parseJwt(action.payload.token);

      //check if userId and user.username is the same
      if (userId === user.username) {
        return {
          ...state,
          isAuthenticated: true,
          user,
          token: action.payload.token

        };
      }

      return {
        ...state,
        isAuthenticated: false,
        user: null,
        token: null
      }

    }
    case "LOGOUT": {
      return {
        ...state,
        isAuthenticated: false,
        user: null,
      };
    }
    case "REGISTER": {
      const { user } = action.payload;

      return {
        ...state,
        isAuthenticated: true,
        user,
      };
    }
    case "PROFILE": {
      const { profile } = action.payload;

      return {
        ...state,
        profile,
        isAuthenticated: true
      };
    }
    default: {
      return { ...state };
    }
  }
};

const parseJwt = (token) => {
  try {
    return JSON.parse(window.atob(token.split('.')[1]));
  } catch (e) {
    return null;
  }
};

const AuthContext1 = createContext({
  ...initialState,
  method: "JWT",
  login: () => Promise.resolve(),
  logout: () => { },
  register: () => Promise.resolve(),
  getProfile: () => Promise.resolve(),
});

export const AuthProvider1 = ({ children }) => {

  const [state, dispatch] = useReducer(reducer, initialState);


  const login = async (email, password, isRememberMe) => {

    let encodePassword = encodeString(password);

    const response = await axios.post("/api/auth/login", { id: email, password: encodePassword });
    const { user } = response.data;

    if (response.data.err) {
      return response.data;
    }

    const token = response.data.token;

    setSession(response.data.token);
    setSession(token);

    dispatch({
      type: "LOGIN",
      payload: {
        user,
        token: token
      },
    });

    //NINA dispatch the call to retrieve the allowed routepaths
    //this is to retrict the shipside to do officer actions, but routepath for VIEW is the same
    //for both users. Buttons are hidden as it is controlled by the controls in the backend.

    // dispatch(getDynamicNavigationByUser());
    return response.data;
  };

  const register = async (email, username, password) => {
    const response = await axios.post("/api/auth/register", {
      email,
      username,
      password,
    });

    const { accessToken, user } = response.data;

    setSession(accessToken);

    dispatch({
      type: "REGISTER",
      payload: {
        user,
      },
    });
  };

  const logout = () => {
    axios.post("/api/logout")
      .then(res => {
        setSession(null);
        dispatch({
          type: "LOGOUT",
          payload: {
            token: null,
            isAuthenticated: false,
            user: null,
          },
        });
      })
      .catch(err => {
        console.log(err);
      });

  };

  const getProfile = async () => {

    try {
      const accessToken = window.localStorage.getItem("accessToken");

      if (accessToken && isValidToken(accessToken)) {
        setSession(accessToken);
        const response = await axios.get("/api/co/cac/profile/");

        if (response && response.err) {
          dispatch({
            type: "INIT",
            payload: {
              isAuthenticated: false,
            },
          });
        } else {
          const { user } = response.data;

          dispatch({
            type: "PROFILE",
            payload: {
              profile: user,
              isAuthenticated: true
            },
          });

        }

      } else {
        dispatch({
          type: "INIT",
          payload: {
            isAuthenticated: false,
            user: null,
          },
        });
      }
    } catch (err) {
      console.error(err);
      dispatch({
        type: "INIT",
        payload: {
          isAuthenticated: false,
          user: null,
        },
      });
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const accessToken = window.localStorage.getItem("accessToken");

        if (accessToken && isValidToken(accessToken)) {
          setSession(accessToken);
          const response = await axios.get("/api/co/cac/profile/");

          if (response && response.err) {
            dispatch({
              type: "INIT",
              payload: {
                isAuthenticated: false,
              },
            });
          } else {
            const { user } = response.data;

            dispatch({
              type: "INIT",
              payload: {
                isAuthenticated: true,
                user,
              },
            });
          }

        } else {
          dispatch({
            type: "INIT",
            payload: {
              isAuthenticated: false,
              user: null,
            },
          });
        }
      } catch (err) {
        console.error(err);
        dispatch({
          type: "INIT",
          payload: {
            isAuthenticated: false,
            user: null,
          },
        });
      }
    })();
  }, []);

  if (!state.isInitialised) {
    return <MatxLoading />;
  }

  return (
    <AuthContext1.Provider
      value={{
        ...state,
        method: "JWT",
        login,
        logout,
        register,
        getProfile,
      }}
    >
      {children}
    </AuthContext1.Provider>
  );
};

export default AuthContext1;
