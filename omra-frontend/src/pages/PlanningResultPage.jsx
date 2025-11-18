import { useLocation, useNavigate } from "react-router-dom";

export default function PlanningResultPage() {
  const { state } = useLocation();
  const navigate = useNavigate();

  if (!state) {
    return (
      <div style={{ minHeight: "100vh", background: "#0f172a", color: "white", padding: "1.5rem" }}>
        <p>Aucun plan calculé. Retour à la configuration.</p>
        <button
          style={{ marginTop: "1rem", padding: "0.5rem", background: "#059669", border: "none", borderRadius: "0.375rem" }}
          onClick={() => navigate("/planning")}
        >
          Configurer un plan
        </button>
      </div>
    );
  }

  const { flight, mekkeHotel, medineHotel, mekkeDays, medineDays, totalCost } = state;

  return (
    <div style={{ minHeight: "100vh", background: "#0f172a", color: "white", padding: "1.5rem" }}>
      <h1 style={{ fontSize: "1.5rem", marginBottom: "1rem" }}>Plan optimal proposé</h1>
      <p>Coût total estimé : <strong>{totalCost}</strong></p>
      <p>Jours à Mekke : {mekkeDays}</p>
      <p>Jours à Médine : {medineDays}</p>

      <h2 style={{ fontSize: "1.25rem", marginTop: "1rem" }}>Vol sélectionné</h2>
      <pre style={{ background: "#1e293b", padding: "0.75rem", borderRadius: "0.5rem" }}>
        {JSON.stringify(flight, null, 2)}
      </pre>

      <h2 style={{ fontSize: "1.25rem", marginTop: "1rem" }}>Hôtel à Mekke</h2>
      <pre style={{ background: "#1e293b", padding: "0.75rem", borderRadius: "0.5rem" }}>
        {JSON.stringify(mekkeHotel, null, 2)}
      </pre>

      <h2 style={{ fontSize: "1.25rem", marginTop: "1rem" }}>Hôtel à Médine</h2>
      <pre style={{ background: "#1e293b", padding: "0.75rem", borderRadius: "0.5rem" }}>
        {JSON.stringify(medineHotel, null, 2)}
      </pre>

      <button
        style={{ marginTop: "1rem", padding: "0.5rem", background: "#059669", borderRadius: "0.375rem", border: "none" }}
        onClick={() => navigate("/planning")}
      >
        Recalculer
      </button>
    </div>
  );
}
