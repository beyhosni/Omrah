import React from "react";
import { StyledButton } from "./Button.styles";

const Button = ({ 
  children, 
  type = "button", 
  onClick, 
  disabled = false,
  variant = "primary",
  ...props 
}) => {
  return (
    <StyledButton
      type={type}
      onClick={onClick}
      disabled={disabled}
      variant={variant}
      {...props}
    >
      {children}
    </StyledButton>
  );
};

export default Button;
