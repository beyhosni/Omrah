import styled from "styled-components";

export const HeaderContainer = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  background-color: #1e293b;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export const HeaderTitle = styled.h1`
  font-size: 1.5rem;
  margin: 0;
  cursor: pointer;
`;

export const HeaderButton = styled.button`
  background: #059669;
  border: none;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s;

  &:hover {
    background: #047857;
  }
`;
