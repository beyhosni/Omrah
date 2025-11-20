import React from "react";
import { useNavigate } from "react-router-dom";
import { HeaderContainer, HeaderButton, HeaderTitle } from "./Header.styles";

const Header = ({ isAuthenticated, onLogout }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    onLogout();
    navigate("/login");
  };

  return (
    <HeaderContainer>
      <HeaderTitle onClick={() => navigate("/planning")}>Omra Planner</HeaderTitle>
      {isAuthenticated && (
        <HeaderButton onClick={handleLogout}>Déconnexion</HeaderButton>
      )}
    </HeaderContainer>
  );
};

export default Header;
