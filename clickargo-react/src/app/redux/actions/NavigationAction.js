import axios from 'axios.js';
import { navigations } from "../../navigations";
import { AccountTypes } from "app/c1utils/const";

export const SET_USER_NAVIGATION = "SET_USER_NAVIGATION";


const getfilteredNavigations = (navList = [], role) => {
  return navList.reduce((array, nav) => {
    if (nav.auth) {
      if (nav.auth.includes(role)) {
        array.push(nav);
      }
    } else {
      if (nav.children) {
        nav.children = getfilteredNavigations(nav.children, role);
        array.push(nav);
      } else {
        array.push(nav);
      }
    }
    return array;
  }, []);
};

export function getNavigationByUser() {
  return (dispatch, getState) => {
    let { user, navigations = [] } = getState();

    let filteredNavigations = getfilteredNavigations(navigations, user.role);

    dispatch({
      type: SET_USER_NAVIGATION,
      payload: [...filteredNavigations]
    });
  };
}

/**Retrieves the menu from backend. */
export function getMenuByUser(user) {
  return (dispatch, getState) => {

    axios({
      method: 'get',
      url: `/api/v1/clickargo/auth/menu`,
    }).then(response => {
      // 20211013 - Have to check for the daily activity
      let accnType = user.coreAccn.TMstAccnType.atypId;
      let accnId = user.coreAccn.accnId;
      let menu = response.data;
      let updatedMenu = menu.filter(m => {
        //Check the nav menu name first, then the account type
        if (m.name === 'Daily Activity Board') {
          //check the account type is ACC_TYPE_PORT or ACC_TYPE_MPWT before checking the menu
          if (accnType && (accnType === AccountTypes.ACC_TYPE_PORT.code || accnType === AccountTypes.ACC_TYPE_MPWT.code)) {
            let nc = m.children.filter((cm) => {
              if (accnId) {
                if (accnId === 'PPAPPORT' && cm.name.includes('PPAP')) {
                  return cm;
                } else if (accnId === 'PASPORT' && cm.name.includes('PAS')) {
                  return cm;
                } else if (accnId === 'MMD' && cm.name.includes('MPWT')) {
                  return cm;
                }
              }
            });

            m.children = nc;
            return m;
          }
        } if (m.name !== 'Daily Activity Board') {
          return m;
        }
      });

      dispatch({
        type: SET_USER_NAVIGATION,
        payload: updatedMenu
      });

    }, (error) => {
      console.log("error", error);
    }).catch(error => {
      console.log("caught error", error);
    });


  };
}


export function getDynamicNavigationByUser() {
  return (dispatch, getState) => {
    axios.defaults.withCredentials = false;
    //login
    const authData = {
      email: 'test3@test.com',
      password: 'password',
      returnSecureToken: true
    }

    let token = null;
    let url = 'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBxoOvpuWs2XEEZ1bNqz8yx2Enn6Z2GCy4';
    axios.post(url, authData)
      .then(response => {
        token = response.data.idToken;
        getNavigations(dispatch, response.data.localId);
      })
      .catch(err => {
        console.log(err);

      });



  };
}

const getNavigations = (dispatch, token) => {

  let navs = navigations;

  // console.log("navigations", response.data);
  //the dispatch below is the argument from this function
  let arr = [];
  //  for (var k in response.data) {
  //arr.push(response.data[k]);
  //}

  dispatch({
    type: SET_USER_NAVIGATION,
    payload: [...navs]
  });
}
