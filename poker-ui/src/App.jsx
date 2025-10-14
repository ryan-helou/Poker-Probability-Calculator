import { useState } from "react";

export default function App() {
  const [value1, setValue1] = useState(12);
  const [suit1, setSuit1] = useState("Spades");
  const [value2, setValue2] = useState(14);
  const [suit2, setSuit2] = useState("Clubs");
  const [players, setPlayers] = useState(4);
  const [iterations, setIterations] = useState(50000);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [result, setResult] = useState(null);

  const suits = ["Spades", "Hearts", "Clubs", "Diamonds"];
  const values = Array.from({ length: 13 }, (_, i) => i + 2); // 2..14 (A=14)

  const calc = async () => {
    setLoading(true);
    setError(null);
    setResult(null);
    try {
      const params = new URLSearchParams({
        value1: String(value1),
        suit1,
        value2: String(value2),
        suit2,
        players: String(players),
        iterations: String(iterations),
      });
      const res = await fetch(`/api/odds?${params.toString()}`);
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json();
      setResult(data);
    } catch (e) {
      setError(e.message ?? "Request failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{ maxWidth: 720, margin: "2rem auto", fontFamily: "system-ui" }}
    >
      <h1>Poker Odds</h1>

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
        <div>
          <label>Hole 1 value</label>
          <br />
          <select value={value1} onChange={(e) => setValue1(+e.target.value)}>
            {values.map((v) => (
              <option key={v} value={v}>
                {v}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Hole 1 suit</label>
          <br />
          <select value={suit1} onChange={(e) => setSuit1(e.target.value)}>
            {suits.map((s) => (
              <option key={s} value={s}>
                {s}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Hole 2 value</label>
          <br />
          <select value={value2} onChange={(e) => setValue2(+e.target.value)}>
            {values.map((v) => (
              <option key={v} value={v}>
                {v}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Hole 2 suit</label>
          <br />
          <select value={suit2} onChange={(e) => setSuit2(e.target.value)}>
            {suits.map((s) => (
              <option key={s} value={s}>
                {s}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Players</label>
          <br />
          <input
            type="number"
            min={2}
            max={9}
            value={players}
            onChange={(e) => setPlayers(+e.target.value)}
          />
        </div>
        <div>
          <label>Iterations</label>
          <br />
          <input
            type="number"
            min={1000}
            step={1000}
            value={iterations}
            onChange={(e) => setIterations(+e.target.value)}
          />
        </div>
      </div>

      <button
        onClick={calc}
        disabled={loading}
        style={{ marginTop: 16, padding: "10px 16px" }}
      >
        {loading ? "Calculating…" : "Calculate"}
      </button>

      {error && <p style={{ color: "crimson" }}>{error}</p>}

      {result && (
        <div style={{ marginTop: 16 }}>
          <h2>Result</h2>
          <ul>
            <li>Win: {Number(result.win).toFixed(2)}%</li>
            <li>Tie: {Number(result.tie).toFixed(2)}%</li>
            <li>Lose: {Number(result.lose).toFixed(2)}%</li>
          </ul>
        </div>
      )}
    </div>
  );
}
