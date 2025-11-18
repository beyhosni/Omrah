import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import PlanningConfigPage from "./pages/PlanningConfigPage";
import PlanningResultPage from "./pages/PlanningResultPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/planning" element={<PlanningConfigPage />} />
        <Route path="/planning/result" element={<PlanningResultPage />} />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}
