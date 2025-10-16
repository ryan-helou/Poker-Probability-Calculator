🔗 **Live Demo:** [https://ryan-helou.github.io/Poker-Probability-Calculator/](https://ryan-helou.github.io/Poker-Probability-Calculator/)

# ♠ Poker Probability Calculator ♥  

A visually rich and interactive **React web app** that simulates poker hands to calculate win, tie, and loss probabilities.  
Built with a **sleek glassmorphism interface**, animated **Iridescent shader background**, and realistic **card rendering** — this app makes probability simulation feel like a casino-grade experience. 🎲

---

## 🚀 Features
- 🎴 Choose any **two hole cards** (value & suit)
- 👥 Adjust **number of players** (2–9)
- 🧮 Run **Monte Carlo simulations** (configurable iterations)
- 📊 Visual **win / tie / lose** bars with percentage display
- ⚡ Handles API responses and **error messages gracefully**
- 💫 Dynamic animated background using **WebGL (OGL.js)**
- 🪞 Sleek **glassmorphism** and red-black poker-themed design

---

## 🧠 Tech Stack
- **React** (Hooks: `useState`, `useEffect`)
- **Vite** — fast development and optimized builds
- **OGL.js** — custom WebGL shader animation background (`Iridescence.jsx`)
- **Vanilla CSS** — responsive layout, gradients, blur, transitions
- **Fetch API** — connects to `/api/odds` endpoint for real-time probability computation

---

## 🃏 How It Works
1. Select your **two hole cards**.
2. Choose the **number of opponents** and **simulation count**.
3. Hit **Calculate Odds**.
4. The app calls `/api/odds` with your parameters and displays:
   - ✅ Win probability (green)
   - 🤝 Tie probability (yellow)
   - ❌ Lose probability (red)

---

## 🎨 Design Highlights
- **Animated Background:** `Iridescence.jsx` renders a live, color-shifting pattern with mouse interaction  
- **Card Styling:** Custom vector-like cards with realistic ranks, suits, and hover animations  
- **Responsive Layout:** Scales beautifully across devices, from desktop to mobile  

---

## 🧩 Folder Structure
```
src/
 ├── App.jsx            # Main UI & logic
 ├── App.css            # Styling for layout, cards, controls, results
 ├── Iridescence.jsx    # WebGL shader animation background
 ├── Iridescence.css    # Canvas positioning and layering
 ├── main.jsx           # React entry point
 └── index.css          # Base styles and resets
```

---

## ✅ License
MIT — use, modify, and distribute freely.  
If you like it, consider leaving a ⭐ on GitHub!

---

## 🙌 Acknowledgments
Inspired by casino-style interfaces, Monte Carlo simulations, and the React creative coding community.
