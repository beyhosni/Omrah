import styled from "styled-components";

const getVariantStyles = (variant) => {
  switch (variant) {
    case "primary":
      return `
        background-color: #059669;
        color: white;

        &:hover:not(:disabled) {
          background-color: #047857;
        }
      `;
    case "secondary":
      return `
        background-color: #475569;
        color: white;

        &:hover:not(:disabled) {
          background-color: #334155;
        }
      `;
    default:
      return `
        background-color: #059669;
        color: white;

        &:hover:not(:disabled) {
          background-color: #047857;
        }
      `;
  }
};

export const StyledButton = styled.button`
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  border: none;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;

  ${({ variant }) => getVariantStyles(variant)}

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;
