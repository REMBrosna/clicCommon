import React, { useEffect, useState } from "react";
import { MatxHorizontalNav } from "matx";
import { navigations } from "../../navigations";
import { makeStyles } from "@material-ui/core/styles";
import clsx from "clsx";
import { useDispatch } from "react-redux";
import { getMenuByUser } from "app/redux/actions/NavigationAction";
import useAuth from 'app/hooks/useAuth';

const useStyles = makeStyles(({ palette, ...theme }) => ({
  root: {
    "&, & .horizontal-nav ul ul": {
      background: '#fff'
    },
    "& .horizontal-nav a, & .horizontal-nav label": {
      color: '#000'
    },
    "& .horizontal-nav ul li ul li:hover, & .horizontal-nav ul li ul li.open": {
      background: palette.primary.dark,
    },
  },
}));



const Layout2Navbar = () => {
  const dispatch = useDispatch();
  const classes = useStyles();
  const { user } = useAuth();

  const [maxSize, setMaxSize] = useState(9);
  useEffect(() => {
    dispatch(getMenuByUser(user));

    if (window.innerWidth < 1600) {
      setMaxSize(5);
    }

    //to recalculate the max no. of menus visible in the navbar
    window.addEventListener('resize', () => {
      if (window.innerWidth < 1600) {
        setMaxSize(5);
      } else {
        setMaxSize(9);
      }
    });
  }, []);

  return (
    <div className={clsx("navbar", classes.root)}>
      <div className="pl-6">
        <MatxHorizontalNav navigation={navigations} max={maxSize} />
      </div>
    </div>
  );
};

export default Layout2Navbar;
