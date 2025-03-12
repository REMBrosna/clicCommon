import { Box, Typography } from "@material-ui/core";
import { grey } from "@material-ui/core/colors";
import React from "react";
import PropTypes from "prop-types";
import { useState } from "react";
import { useEffect } from "react";
import { useRef } from "react";

const RedirecMessage = (props) => {
  const { service, timer = 5, path } = props;

  const [num, setNum] = useState(timer);

  let intervalRef = useRef();

  const decreaseNum = () => setNum((prev) => prev - 1);

  useEffect(() => {
    intervalRef.current = setInterval(decreaseNum, 1000);

    return () => clearInterval(intervalRef.current);
  }, []);

  useEffect(() => {
    if (num === 0) {
      clearInterval(intervalRef.current);
      console.log("path: ", path);

      if (service.toLowerCase() === "clicdo") {
        window.location.href = `${
          process.env.REACT_APP_CLICDO_URL
        }/session/sso?token=${window.localStorage.getItem("accessToken")}`;
      } else {
        window.location.href = path;
      }
    }
  }, [num]);

  return (
    <>
      <Box
        component={"div"}
        style={{
          display: "flex",
          width: "100%",
          justifyContent: "center",
          marginTop: 20,
          color: grey[500],
          marginBottom: -20,
        }}
      >
        <Box
          style={{
            border: `1px solid ${grey[500]}`,
            borderRadius: 8,
            padding: 10,
          }}
        >
          <Typography>
            You will be redirected automatically to {service} in {num} second
          </Typography>
        </Box>
      </Box>
    </>
  );
};

RedirecMessage.propTypes = {
  service: PropTypes.string,
  timer: PropTypes.number,
  path: PropTypes.string,
};

export default RedirecMessage;
