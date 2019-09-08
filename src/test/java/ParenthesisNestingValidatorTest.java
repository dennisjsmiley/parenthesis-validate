import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aircastle.ParenthesisNestingValidator;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ParenthesisNestingValidatorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testIsProperlyNestedSingle() {
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


        for (Map.Entry<String, Boolean> testCase : testCasesSingle.entrySet()) {
            runTestCase(testCase.getKey(), testCase.getValue());
        }

    }

    @Test
    public void testIsProperlyNestedMulti() {
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

        for(Map.Entry<String, Boolean> testCase : testCasesMulti.entrySet()) {
            runTestCase(testCase.getKey(), testCase.getValue());
        }
    }

    private void runTestCase(String expression, boolean expected) {
        ParenthesisNestingValidator validator = new ParenthesisNestingValidator();
        boolean actual = validator.isProperlyNested(expression);
        logger.info("expression: {}, expected validity: {}, actual validity: {}", expression, expected, actual);
        assertEquals(expected, actual);
    }
}
