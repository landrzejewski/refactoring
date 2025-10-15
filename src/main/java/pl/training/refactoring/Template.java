package pl.training.refactoring;

import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Template {

    private static final String EXPRESSION_START = "\\$\\{";
    private static final String EXPRESSION_END = "}";
    private static final Pattern EXPRESSION = Pattern.compile(EXPRESSION_START + "\\w+" + EXPRESSION_END);
    private static final String INVALID_VALUE = ".*\\W+.*";

    private final String text;

    public Template(String text) {
        this.text = text;
    }

    public String evaluate(final Map<String, String> data) {
        if (!isComplete(data) || !isValid(data)) {
            throw new IllegalArgumentException();
        }
        return substitute(data);
    }

    private boolean isComplete(final Map<String, String> data) {
        return data.size() == getExpressions().count();
    }

    private boolean isValid(final Map<String, String> data) {
        return data.values().stream().noneMatch(value -> value.matches(INVALID_VALUE));
    }

    private String substitute(final Map<String, String> data) {
        var result = text;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            var expression = createExpression(entry.getKey());
            result = result.replaceAll(expression, entry.getValue());
        }
        return result;
    }

    private String createExpression(final String key) {
        return EXPRESSION_START + key + EXPRESSION_END;
    }

    private Stream<MatchResult> getExpressions() {
        return EXPRESSION.matcher(text).results();
    }

}
