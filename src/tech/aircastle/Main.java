package tech.aircastle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static int[] newOpen() {
        return new int[] {0,0,0};
    }

    private static boolean isValidPriv(String expression) {
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

    public static void main(String[] args) {
        Map<String, Boolean> testCasesSingle = new HashMap<>();
        testCasesSingle.put(
                "(lkewdn + \\ /+1(bsdiIUWE)klkl                 () 67)",
                Boolean.TRUE
        );
        testCasesSingle.put(
                "(lkewdn + \\ /+1(bsdiIUWE)kl - )kl            () 67)",
                Boolean.FALSE
        );
        testCasesSingle.put(
                "()",
                Boolean.TRUE
        );
        testCasesSingle.put(
                "()()(",
                Boolean.FALSE
        );
        testCasesSingle.put(
                ")()",
                Boolean.FALSE
        );
        testCasesSingle.put(
                "()()(())",
                Boolean.TRUE
        );


        int failsSingle = 0;
        for(String test : testCasesSingle.keySet()) {
            if(isValidPriv(test) != testCasesSingle.get(test)) {
                failsSingle ++;
            }
        }


        Map<String, Boolean> testCasesMulti = new HashMap<String, Boolean>();
        testCasesMulti.put(
                "(lkewdn +{ \\ /+1}(bsdiI[UW]E)klkl          [()  777]67)",
                Boolean.TRUE
        );
        testCasesMulti.put(
                "({lkewdn +{ \\ /+1}(bsdiI[UW]E)klkl         [}()  777]67)",
                Boolean.FALSE
        );
        testCasesMulti.put(
                "()[]",
                Boolean.TRUE
        );
        testCasesMulti.put(
                "([])",
                Boolean.TRUE
        );
        testCasesMulti.put(
                "(][])",
                Boolean.FALSE
        );
        testCasesMulti.put(
                "]()",
                Boolean.FALSE
        );
        testCasesMulti.put(
                "() ({[] () })",
                Boolean.TRUE
        );
        testCasesMulti.put(
                "() ({[] ) })",
                Boolean.FALSE
        );
        testCasesMulti.put(
                "(])",
                Boolean.FALSE
        );

        int failsMulti = 0;
        for(String test : testCasesMulti.keySet()) {
            if(isValidPriv(test) != testCasesMulti.get(test)) {
                failsMulti ++;
            }
        }


        String text = "";
        text += "failed tests (single): " + failsSingle + " / " + testCasesSingle.size() + "\n";
        text += "failed tests (multi) : " + failsMulti  + " / " + testCasesMulti.size()  + "\n";

        System.out.println(text);
    }
}
