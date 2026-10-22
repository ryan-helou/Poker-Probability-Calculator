// Texas Hold'em win odds by Monte Carlo simulation, entirely in the browser.
// Mirrors Round.calculateOdds in the Java version: deal everyone, share a 5-card board,
// win = strictly best hand, tie = equal to the best opponent, lose = otherwise.

const SUIT_INDEX = { Spades: 0, Hearts: 1, Clubs: 2, Diamonds: 3 };

// card = rank (2-14) * 4 + suit (0-3)
const cardId = (rank, suit) => rank * 4 + SUIT_INDEX[suit];

// Best 5-card hand out of 7 as one comparable number: category * 15^5 + packed kickers.
export function scoreHand(cards) {
  const ranks = new Array(15).fill(0);
  const suitCards = [[], [], [], []];
  for (const c of cards) {
    const r = c >> 2;
    ranks[r]++;
    suitCards[c & 3].push(r);
  }
  const pack = (list) => list.slice(0, 5).reduce((acc, r) => acc * 15 + r, 0);
  const padded = (list) => { const l = list.slice(0, 5); while (l.length < 5) l.push(0); return pack(l); };

  const straightHigh = (has) => {
    for (let high = 14; high >= 5; high--) {
      let ok = true;
      for (let k = 0; k < 5; k++) if (!has(high - k)) { ok = false; break; }
      if (ok) return high;
    }
    return has(14) && has(2) && has(3) && has(4) && has(5) ? 5 : 0; // wheel
  };

  const flushSuit = suitCards.findIndex((s) => s.length >= 5);
  if (flushSuit >= 0) {
    const have = new Set(suitCards[flushSuit]);
    const sf = straightHigh((r) => have.has(r));
    if (sf) return 8 * 15 ** 5 + sf;
  }

  const groups = [];
  for (let r = 14; r >= 2; r--) if (ranks[r]) groups.push([ranks[r], r]);
  groups.sort((a, b) => b[0] - a[0] || b[1] - a[1]);
  const [c0, r0] = groups[0];
  const second = groups[1];

  if (c0 === 4) {
    const kicker = groups.slice(1).map((g) => g[1]).sort((a, b) => b - a)[0] || 0;
    return 7 * 15 ** 5 + r0 * 15 + kicker;
  }
  if (c0 === 3 && second && second[0] >= 2) return 6 * 15 ** 5 + r0 * 15 + second[1];

  if (flushSuit >= 0) return 5 * 15 ** 5 + padded(suitCards[flushSuit].slice().sort((a, b) => b - a));

  const st = straightHigh((r) => ranks[r] > 0);
  if (st) return 4 * 15 ** 5 + st;

  const singles = groups.filter((g) => g[0] === 1).map((g) => g[1]);
  if (c0 === 3) return 3 * 15 ** 5 + padded([r0, ...singles.slice(0, 2)]);
  if (c0 === 2 && second && second[0] === 2) {
    const pairs = groups.filter((g) => g[0] === 2).map((g) => g[1]).slice(0, 2);
    const rest = groups.filter((g) => !pairs.includes(g[1])).map((g) => g[1]).sort((a, b) => b - a);
    return 2 * 15 ** 5 + padded([...pairs, rest[0] || 0]);
  }
  if (c0 === 2) return 1 * 15 ** 5 + padded([r0, ...singles.slice(0, 3)]);
  return padded(singles);
}

export function simulateOdds({ value1, suit1, value2, suit2, players, iterations }) {
  const hero1 = cardId(value1, suit1);
  const hero2 = cardId(value2, suit2);
  if (hero1 === hero2 || value1 < 2 || value1 > 14 || value2 < 2 || value2 > 14) return { win: 0, tie: 0, lose: 0 };

  const deck = [];
  for (let r = 2; r <= 14; r++) for (let s = 0; s < 4; s++) { const id = r * 4 + s; if (id !== hero1 && id !== hero2) deck.push(id); }

  const need = (players - 1) * 2 + 5;
  let win = 0, tie = 0, lose = 0;
  for (let i = 0; i < iterations; i++) {
    // partial Fisher-Yates: shuffle only the first `need` cards
    for (let k = 0; k < need; k++) {
      const j = k + Math.floor(Math.random() * (deck.length - k));
      const t = deck[k]; deck[k] = deck[j]; deck[j] = t;
    }
    const board = deck.slice(need - 5, need);
    const hero = scoreHand([hero1, hero2, ...board]);
    let best = -1;
    for (let p = 0; p < players - 1; p++) {
      const sc = scoreHand([deck[p * 2], deck[p * 2 + 1], ...board]);
      if (sc > best) best = sc;
    }
    if (hero > best) win++; else if (hero < best) lose++; else tie++;
  }
  return { win: (win * 100) / iterations, tie: (tie * 100) / iterations, lose: (lose * 100) / iterations };
}
