import styled from "styled-components";

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 0.75rem;
`;

export const InputLabel = styled.label`
  margin-bottom: 0.25rem;
  font-weight: 500;
`;

export const StyledInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border-radius: 0.25rem;
  border: 1px solid #475569;
  background-color: #334155;
  color: white;

  &:focus {
    outline: none;
    border-color: #059669;
    box-shadow: 0 0 0 1px #059669;
  }
`;
