import { useState } from "react";
import api from "../api";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      const res = await api.post("/api/auth/login", { email, password });
      localStorage.setItem("token", res.data.token);
      window.location.href = "/planning";
    } catch (err) {
      setError("Email ou mot de passe incorrect");
    }
  };

  return (
    <div style={{ minHeight: "100vh", display: "flex", justifyContent: "center", alignItems: "center", background: "#0f172a", color: "white" }}>
      <div style={{ background: "#1e293b", padding: "2rem", borderRadius: "0.75rem", width: "100%", maxWidth: 400 }}>
        <h1 style={{ fontSize: "1.5rem", marginBottom: "1rem", textAlign: "center" }}>Omra Planner</h1>
        <form onSubmit={handleSubmit} style={{ display: "grid", gap: "0.75rem" }}>
          <div>
            <label>Email</label>
            <input
              style={{ width: "100%", padding: "0.5rem", color: "black" }}
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Mot de passe</label>
            <input
              style={{ width: "100%", padding: "0.5rem", color: "black" }}
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <p style={{ color: "rgb(248,113,113)", fontSize: "0.875rem" }}>{error}</p>}
          <button style={{ padding: "0.5rem", background: "#059669", borderRadius: "0.375rem", border: "none" }}>
            Se connecter
          </button>
        </form>
      </div>
    </div>
  );
}
