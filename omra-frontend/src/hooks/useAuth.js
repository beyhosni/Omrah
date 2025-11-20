import { useState, useEffect, useContext, createContext } from "react";
import { AuthService } from "../services/AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkAuthStatus = () => {
      const authenticated = AuthService.isAuthenticated();
      setIsAuthenticated(authenticated);
      setIsLoading(false);
    };

    checkAuthStatus();
  }, []);

  const login = async (email, password) => {
    try {
      await AuthService.login(email, password);
      setIsAuthenticated(true);
      return true;
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    AuthService.logout();
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        isLoading,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
