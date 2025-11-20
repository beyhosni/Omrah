import React from "react";
import { InputContainer, InputLabel, StyledInput } from "./Input.styles";

const Input = ({ 
  label, 
  type = "text", 
  value, 
  onChange, 
  placeholder, 
  required = false,
  ...props 
}) => {
  return (
    <InputContainer>
      <InputLabel>{label}</InputLabel>
      <StyledInput
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
        {...props}
      />
    </InputContainer>
  );
};

export default Input;
