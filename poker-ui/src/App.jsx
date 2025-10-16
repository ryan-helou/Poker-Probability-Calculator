import { useState } from "react";
import "./App.css";
import Iridescence from "./Iridescence";

export default function App() {
  const [value1, setValue1] = useState(14);
  const [suit1, setSuit1] = useState("Spades");
  const [value2, setValue2] = useState(13);
  const [suit2, setSuit2] = useState("Spades");
  const [players, setPlayers] = useState(4);
  const [iterations, setIterations] = useState(50000);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [result, setResult] = useState(null);

  const suits = ["Spades", "Hearts", "Clubs", "Diamonds"];
  const values = [
    { val: 2, display: "2" },
    { val: 3, display: "3" },
    { val: 4, display: "4" },
    { val: 5, display: "5" },
    { val: 6, display: "6" },
    { val: 7, display: "7" },
    { val: 8, display: "8" },
    { val: 9, display: "9" },
    { val: 10, display: "10" },
    { val: 11, display: "J" },
    { val: 12, display: "Q" },
    { val: 13, display: "K" },
    { val: 14, display: "A" },
  ];

  const suitSymbols = {
    Spades: "♠",
    Hearts: "♥",
    Clubs: "♣",
    Diamonds: "♦",
  };

  const suitColors = {
    Spades: "#000",
    Hearts: "#C8102E",
    Clubs: "#000",
    Diamonds: "#C8102E",
  };

  const getDisplayValue = (val) => {
    const found = values.find((v) => v.val === val);
    return found ? found.display : val;
  };

  const validateInputs = () => {
    if (value1 === value2 && suit1 === suit2) {
      return "You can't pick the exact same card twice.";
    }
    if (players < 2 || players > 9) {
      return "Number of players must be between 2 and 9.";
    }
    if (iterations < 1000) {
      return "Iterations must be at least 1,000.";
    }
    return null;
  };

  const calc = async () => {
    setLoading(true);
    setError(null);
    setResult(null);

    const v = validateInputs();
    if (v) {
      setLoading(false);
      setError(v);
      return;
    }

    const statusMessages = {
      400: "Invalid input. Please check your selections and try again.",
      404: "Service not found. Is the API route correct?",
      422: "Invalid input. Please correct the fields and try again.",
      429: "Too many requests. Please wait a moment and retry.",
      500: "Server error while calculating odds. Please try again in a moment.",
      503: "Service unavailable. Please try again shortly.",
    };

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

      if (!res.ok) {
        const ct = res.headers.get("content-type") || "";
        let serverMsg = "";
        if (ct.includes("application/json")) {
          const data = await res.json().catch(() => null);
          serverMsg = data?.error || data?.message || "";
        } else {
          serverMsg = await res.text().catch(() => "");
        }
        const fallback =
          statusMessages[res.status] || `Unexpected error (${res.status}).`;
        throw new Error(serverMsg.trim() || fallback);
      }

      const data = await res.json();
      setResult(data);
    } catch (e) {
      const msg = String(e?.message || "").replace(/^TypeError:\s*/i, "");
      const friendly = msg.includes("Failed to fetch")
        ? "Network error. Check your connection and try again."
        : msg || "Something went wrong. Please try again.";
      setError(friendly);
    } finally {
      setLoading(false);
    }
  };

  const Card = ({ value, suit }) => (
    <div className="card">
      <div className="card-corner top-left">
        <div className="card-value" style={{ color: suitColors[suit] }}>
          {getDisplayValue(value)}
        </div>
        <div className="card-suit" style={{ color: suitColors[suit] }}>
          {suitSymbols[suit]}
        </div>
      </div>

      <div className="card-center" style={{ color: suitColors[suit] }}>
        {suitSymbols[suit]}
      </div>

      <div className="card-corner bottom-right">
        <div className="card-value" style={{ color: suitColors[suit] }}>
          {getDisplayValue(value)}
        </div>
        <div className="card-suit" style={{ color: suitColors[suit] }}>
          {suitSymbols[suit]}
        </div>
      </div>
    </div>
  );

  return (
    <>
      <Iridescence
        color={[0.6, 0, 0]}
        amplitude={0.3}
        speed={0.5}
        mouseReact
      ></Iridescence>
      <div className="container">
        <h1>♠ Poker Probability Calculator ♥</h1>
        <div className="cards-display">
          <Card value={value1} suit={suit1} />
          <Card value={value2} suit={suit2} />
        </div>
        <div className="controls">
          <div className="control-grid">
            <div className="control-group">
              <label>First Card Value</label>
              <select
                value={value1}
                onChange={(e) => setValue1(+e.target.value)}
              >
                {values.map((v) => (
                  <option key={v.val} value={v.val}>
                    {v.display}
                  </option>
                ))}
              </select>
            </div>

            <div className="control-group">
              <label>First Card Suit</label>
              <select value={suit1} onChange={(e) => setSuit1(e.target.value)}>
                {suits.map((s) => (
                  <option key={s} value={s}>
                    {suitSymbols[s]} {s}
                  </option>
                ))}
              </select>
            </div>

            <div className="control-group">
              <label>Second Card Value</label>
              <select
                value={value2}
                onChange={(e) => setValue2(+e.target.value)}
              >
                {values.map((v) => (
                  <option key={v.val} value={v.val}>
                    {v.display}
                  </option>
                ))}
              </select>
            </div>

            <div className="control-group">
              <label>Second Card Suit</label>
              <select value={suit2} onChange={(e) => setSuit2(e.target.value)}>
                {suits.map((s) => (
                  <option key={s} value={s}>
                    {suitSymbols[s]} {s}
                  </option>
                ))}
              </select>
            </div>

            <div className="control-group">
              <label>Number of Players (2-9)</label>
              <input
                type="number"
                min={2}
                max={9}
                value={players}
                onChange={(e) => setPlayers(+e.target.value)}
                onFocus={(e) => e.target.select()}
              />
            </div>

            <div className="control-group">
              <label>Simulation Iterations</label>
              <input
                type="number"
                min={1000}
                step={1000}
                value={iterations}
                onChange={(e) => setIterations(+e.target.value)}
              />
            </div>
          </div>

          <button className="calc-button" onClick={calc} disabled={loading}>
            {loading ? "Calculating..." : "Calculate Odds"}
          </button>

          {error && <div className="error">{error}</div>}
        </div>
        {result && (
          <div className="results">
            <h2>Your Odds</h2>
            <div className="result-bars">
              <div className="result-item">
                <div className="result-header">
                  <span className="result-label">Win</span>
                  <span className="result-value" style={{ color: "#10b981" }}>
                    {Number(result.win).toFixed(2)}%
                  </span>
                </div>
                <div className="result-bar-bg">
                  <div
                    className="result-bar-fill win-bar"
                    style={{ width: `${result.win}%` }}
                  />
                </div>
              </div>

              <div className="result-item">
                <div className="result-header">
                  <span className="result-label">Tie</span>
                  <span className="result-value" style={{ color: "#f59e0b" }}>
                    {Number(result.tie).toFixed(2)}%
                  </span>
                </div>
                <div className="result-bar-bg">
                  <div
                    className="result-bar-fill tie-bar"
                    style={{ width: `${result.tie}%` }}
                  />
                </div>
              </div>

              <div className="result-item">
                <div className="result-header">
                  <span className="result-label">Lose</span>
                  <span className="result-value" style={{ color: "#ef4444" }}>
                    {Number(result.lose).toFixed(2)}%
                  </span>
                </div>
                <div className="result-bar-bg">
                  <div
                    className="result-bar-fill lose-bar"
                    style={{ width: `${result.lose}%` }}
                  />
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
}
