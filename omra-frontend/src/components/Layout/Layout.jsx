import React from "react";
import Header from "./Header";
import { LayoutContainer, MainContent } from "./Layout.styles";

const Layout = ({ children, isAuthenticated, onLogout }) => {
  return (
    <LayoutContainer>
      <Header isAuthenticated={isAuthenticated} onLogout={onLogout} />
      <MainContent>{children}</MainContent>
    </LayoutContainer>
  );
};

export default Layout;
