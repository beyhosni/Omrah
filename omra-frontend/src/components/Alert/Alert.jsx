import React from "react";
import { AlertContainer } from "./Alert.styles";

const Alert = ({ children, type = "error" }) => {
  return (
    <AlertContainer type={type}>
      {children}
    </AlertContainer>
  );
};

export default Alert;
