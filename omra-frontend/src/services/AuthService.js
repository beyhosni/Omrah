import api from "../api";

export const AuthService = {
  login: async (email, password) => {
    try {
      const response = await api.post("/api/auth/login", { email, password });
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
      }
      return response.data;
    } catch (error) {
      throw new Error("Email ou mot de passe incorrect");
    }
  },

  logout: () => {
    localStorage.removeItem("token");
  },

  isAuthenticated: () => {
    return !!localStorage.getItem("token");
  },

  getToken: () => {
    return localStorage.getItem("token");
  }
};
