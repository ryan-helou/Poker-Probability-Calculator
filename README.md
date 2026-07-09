# 🃏 Poker Probability Calculator

*A Texas Hold'em odds calculator: give it your two hole cards and a table size, and it runs a Monte Carlo simulation to report how often you win, tie, or lose.*

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white) ![React](https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=react&logoColor=black) ![Vite](https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)

Nothing here is solved in closed form. Each request pins your two cards as "player one" and then plays out whole hands. Every iteration builds a fresh 52-card deck, removes your two cards, deals two random hole cards to each of the other seats (the table holds 2 to 9), and turns over a random five-card board. It scores your best hand against the best hand at the table and records a win, a tie, or a loss. The reported percentages are just those tallies over the iteration count, which the UI defaults to 50,000 and floors at 1,000.

There is no board input — every runout is dealt fresh — so what comes back is preflop equity for a starting hand against N random holdings, not the odds on a specific flop.

## 🎲 Scoring a hand

Each player ends up with seven cards (two hole plus five board), so the evaluator enumerates all 21 ways to choose five of them. The C(7,5) index patterns are written out as a hardcoded 21×5 table rather than generated at runtime. Every five-card combo is sorted by rank and run down a ladder of boolean classifiers — straight flush, four of a kind, full house, flush, straight, trips, two pair, pair, high card. The matching category produces a single `double` score assembled from powers of ten: the category picks the magnitude band, from 10^28 for a straight flush down to 10^4 for high card in steps of three orders of magnitude, and the ranked kickers fill the lower decimal places. One numeric comparison then settles both hand class and kickers, and the highest of the 21 combos is the player's hand.

It is brute force by design. A 50,000-iteration run at a full nine-handed table scores on the order of ten million five-card hands, which the JVM clears in well under a second.

## 🛠️ Stack

A Spring Boot 3.3.4 / Java 17 service exposes one endpoint, `GET /api/odds`, which takes the two card values and suits, the player count, and the iteration count, and returns `{win, tie, lose}`. The front end is a React + Vite single page — card pickers, animated win/tie/lose bars, and an OGL WebGL iridescence shader behind it all — deployed to GitHub Pages at [ryan-helou.github.io/Poker-Probability-Calculator](https://ryan-helou.github.io/Poker-Probability-Calculator/) and calling the Java API on localhost:8080.
