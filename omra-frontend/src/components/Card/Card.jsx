import React from "react";
import { CardContainer, CardHeader, CardTitle, CardContent } from "./Card.styles";

const Card = ({ title, children }) => {
  return (
    <CardContainer>
      {title && (
        <CardHeader>
          <CardTitle>{title}</CardTitle>
        </CardHeader>
      )}
      <CardContent>{children}</CardContent>
    </CardContainer>
  );
};

export default Card;
