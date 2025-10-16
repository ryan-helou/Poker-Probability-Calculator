import { calculateOdds } from './pokerEngine';

export async function calculatePokerOdds(playerCards, communityCards) {
    // Convert string representations to Card objects
    const convertedPlayerCards = playerCards.map(cardStr => {
        const rank = parseRank(cardStr.charAt(0));
        const suit = parseSuit(cardStr.charAt(1));
        return { rank, suit };
    });

    const convertedCommunityCards = communityCards.map(cardStr => {
        const rank = parseRank(cardStr.charAt(0));
        const suit = parseSuit(cardStr.charAt(1));
        return { rank, suit };
    });

    // Calculate odds using the local poker engine
    const winningPercentage = calculateOdds(convertedPlayerCards, convertedCommunityCards);
    
    return {
        winningPercentage: winningPercentage.toFixed(2)
    };
}

function parseRank(rankChar) {
    const rankMap = {
        '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9,
        'T': 10, 'J': 11, 'Q': 12, 'K': 13, 'A': 14
    };
    return rankMap[rankChar] || parseInt(rankChar);
}

function parseSuit(suitChar) {
    const suitMap = {
        'H': 'hearts',
        'D': 'diamonds',
        'C': 'clubs',
        'S': 'spades'
    };
    return suitMap[suitChar];
}