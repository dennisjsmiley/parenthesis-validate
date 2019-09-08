package tech.aircastle;

import java.util.ArrayList;
import java.util.List;

public class ParenthesisNestingValidator {

    private int[] newOpen() {
        return new int[] {0,0,0};
    }

    public boolean isProperlyNested(String expression) {
        int uniqueParenthesis = newOpen().length;
        int nestLevel = 0;
        List<int[]> nestedOpen = new ArrayList<>();
        nestedOpen.add(newOpen());

        String[] openTokens =  new String[] {"(", "[", "{"};
        String[] closeTokens = new String[] {")", "]", "}"};

        for(int i = 0; i < expression.length(); i++) {
            String cur = String.valueOf(expression.charAt(i));

            for(int j = 0; j < uniqueParenthesis; j++) {

                String openToken = openTokens[j];
                String closeToken = closeTokens[j];
                int[] open;

                if (cur.equals(openToken)) {
                    open = nestedOpen.get(nestLevel);
                    open[j]++;

                    nestLevel++;
                    if (nestedOpen.size() <= nestLevel) {
                        nestedOpen.add(newOpen());
                    }
                }

                if (cur.equals(closeToken)) {
                    if (nestLevel == 0) {
                        return false;
                    }

                    open = nestedOpen.get(--nestLevel);
                    open[j]--;

                    if (open[j] < 0) {
                        return false;
                    }
                }
            }
        }

        int nonZeros = 0;
        for (int[] open : nestedOpen) {
            for (int j = 0; j < open.length; j++) {
                if (open[j] != 0) {
                    nonZeros++;
                }
            }
        }
        return nonZeros == 0;
    }

}
