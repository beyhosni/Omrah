import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function PlanningConfigPage() {
  const [departureCity, setDepartureCity] = useState("Paris");
  const [dateFrom, setDateFrom] = useState("");
  const [dateTo, setDateTo] = useState("");
  const [persons, setPersons] = useState(2);
  const [budget, setBudget] = useState(2500);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post("http://localhost:8084/api/planning/optimal", {
      departureCity,
      dateFrom,
      dateTo,
      persons,
      budget
    });
    navigate("/planning/result", { state: res.data });
  };

  return (
    <div style={{ minHeight: "100vh", background: "#0f172a", color: "white", padding: "1.5rem" }}>
      <h1 style={{ fontSize: "1.5rem", marginBottom: "1rem" }}>Configurer votre Omra</h1>
      <form onSubmit={handleSubmit} style={{ display: "grid", gap: "0.75rem", maxWidth: 480 }}>
        <div>
          <label>Ville de départ</label>
          <input
            style={{ width: "100%", padding: "0.5rem", color: "black" }}
            value={departureCity}
            onChange={(e) => setDepartureCity(e.target.value)}
          />
        </div>
        <div>
          <label>Date de départ</label>
          <input
            type="date"
            style={{ width: "100%", padding: "0.5rem", color: "black" }}
            value={dateFrom}
            onChange={(e) => setDateFrom(e.target.value)}
          />
        </div>
        <div>
          <label>Date de retour</label>
          <input
            type="date"
            style={{ width: "100%", padding: "0.5rem", color: "black" }}
            value={dateTo}
            onChange={(e) => setDateTo(e.target.value)}
          />
        </div>
        <div>
          <label>Nombre de personnes</label>
          <input
            type="number"
            style={{ width: "100%", padding: "0.5rem", color: "black" }}
            value={persons}
            onChange={(e) => setPersons(parseInt(e.target.value || "1", 10))}
          />
        </div>
        <div>
          <label>Budget total (€)</label>
          <input
            type="number"
            style={{ width: "100%", padding: "0.5rem", color: "black" }}
            value={budget}
            onChange={(e) => setBudget(parseInt(e.target.value || "0", 10))}
          />
        </div>
        <button style={{ padding: "0.5rem", background: "#059669", borderRadius: "0.375rem", border: "none", marginTop: "0.5rem" }}>
          Calculer le plan optimal
        </button>
      </form>
    </div>
  );
}
