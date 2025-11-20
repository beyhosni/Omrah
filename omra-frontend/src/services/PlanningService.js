import axios from "axios";

const planningApi = axios.create({
  baseURL: "http://localhost:8084"
});

planningApi.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const PlanningService = {
  getOptimalPlanning: async (planningData) => {
    try {
      const response = await planningApi.post("/api/planning/optimal", planningData);
      return response.data;
    } catch (error) {
      throw new Error("Erreur lors de la génération du planning optimal");
    }
  }
};
