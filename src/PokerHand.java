import java.util.Arrays;
import java.util.Comparator;

public class PokerHand implements Comparable<PokerHand> {
    private String[] cards;

    public PokerHand(String hand) {
        this.cards = hand.split(" ");
        Arrays.sort(this.cards, new Comparator<String>() {
            @Override
            public int compare(String card1, String card2) {
                int rank1 = getRank(card1.charAt(0));
                int rank2 = getRank(card2.charAt(0));
                return Integer.compare(rank1, rank2);
            }
        });
    }

    public Result compareWith(PokerHand hand) {
        int[] ranks1 = getRanks(this.cards);
        int[] ranks2 = getRanks(hand.cards);

        int value1 = getValue(ranks1);
        int value2 = getValue(ranks2);

        if (value1 > value2) {
            return Result.WIN;
        } else if (value1 < value2) {
            return Result.LOSS;
        } else {
            return Result.TIE;
        }
    }

    @Override
    public int compareTo(PokerHand hand) {
        return compareWith(hand).ordinal();
    }

    private int getRank(char rank) {
        switch (rank) {
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                return 0;
        }
    }

    private int[] getRanks(String[] cards) {
        int[] ranks = new int[15];
        for (String card : cards) {
            char rank = card.charAt(0);
            ranks[getRank(rank)]++;
        }
        return ranks;
    }

    private int getValue(int[] ranks) {
        int[] count = new int[6];
        boolean flush = true;
        boolean straight = false;

        for (int i = 2; i <= 14; i++) {
            if (ranks[i] == 1) {
                count[0]++;
                count[1] = i;
            }
            if (ranks[i] == 2) {
                count[2]++;
                count[3] = i;
            }
            if (ranks[i] == 3) {
                count[4]++;
                count[5] = i;
            }
            if (ranks[i] == 4) {
                count[0]++;
                count[1] = i;
            }
        }

        for (int i = 2; i <= 10; i++) {
            if (ranks[i] == 1 && ranks[i + 1] == 1 && ranks[i + 2] == 1 && ranks[i + 3] == 1 && ranks[i + 4] == 1) {
                straight = true;
                count[0] = 5;
                count[1] = i + 4;
                break;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (cards[i].charAt(1) != cards[i + 1].charAt(1)) {
                flush = false;
                break;
            }
        }

        if (straight && flush) return 8;
        if (count[4] == 1) return 7;
        if (count[0] == 4) return 6;
        if (count[2] == 1 && count[4] == 1) return 5;
        if (flush) return 4;
        if (straight) return 3;
        if (count[4] == 1) return 2;
        if (count[2] == 1) return 1;
        return 0;
    }


    @Override
    public String toString() {
        StringBuilder[] lines = new StringBuilder[5];
        for (int i = 0; i < 5; i++) {
            lines[i] = new StringBuilder();
        }

        String[] reversedCards = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            reversedCards[i] = cards[cards.length - i - 1];
        }

        for (String card : reversedCards) { //
            String[] cardLines = getAsciiCard(card).split("\n");
            for (int i = 0; i < 5; i++) {
                lines[i].append(cardLines[i]).append(" ");
            }
        }

        StringBuilder sb = new StringBuilder();
        for (StringBuilder line : lines) {
            sb.append(line.toString().trim()).append("\n");
        }

        return sb.toString();
    }


    private String getAsciiCard(String card) {
        char rank = card.charAt(0);
        char suit = card.charAt(1);

        StringBuilder sb = new StringBuilder();
        sb.append("┌─────┐\n");
        sb.append("│ ").append(rank).append("   │\n");
        sb.append("│  ").append(suit).append("  │\n");
        sb.append("│   ").append(rank).append(" │\n");
        sb.append("└─────┘");

        return sb.toString();
    }
}
