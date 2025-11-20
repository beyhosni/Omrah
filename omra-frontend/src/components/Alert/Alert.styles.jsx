import styled from "styled-components";

const getAlertStyles = (type) => {
  switch (type) {
    case "error":
      return `
        background-color: rgba(248, 113, 113, 0.1);
        color: #f87171;
        border-left: 4px solid #f87171;
      `;
    case "success":
      return `
        background-color: rgba(34, 197, 94, 0.1);
        color: #22c55e;
        border-left: 4px solid #22c55e;
      `;
    case "warning":
      return `
        background-color: rgba(251, 191, 36, 0.1);
        color: #fbbf24;
        border-left: 4px solid #fbbf24;
      `;
    case "info":
      return `
        background-color: rgba(59, 130, 246, 0.1);
        color: #3b82f6;
        border-left: 4px solid #3b82f6;
      `;
    default:
      return `
        background-color: rgba(248, 113, 113, 0.1);
        color: #f87171;
        border-left: 4px solid #f87171;
      `;
  }
};

export const AlertContainer = styled.div`
  padding: 0.75rem;
  border-radius: 0.25rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;

  ${({ type }) => getAlertStyles(type)}
`;
