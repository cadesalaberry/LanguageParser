/**
 * @author cadesalaberry
 */

package a2posted;

/*
 *   STUDENT NAME      :	Charles-Antoine de Salaberry
 *   STUDENT ID        :	260400928
 *   
 *   If you have any issues that you wish the T.A.s to consider, then you
 *   should list them here.   In this case you should add a note in the dropbox 
 *   comment section so that the grader is sure to read this comment.
 *      
 */

/**
 * This static class provides the parse() method to parse a sequence of tokens.
 * You are provided with the helper methods isBoolean() and isAssignment().
 * 
 * - You may add other methods as you deem necessary. - You may NOT add any
 * class fields.
 */

public class LanguageParser {

	/**
	 * Returns true if the given token is a boolean value, i.e. if the token is
	 * "true" or "false".
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isBoolean(String token) {

		return (token.equals("true") || token.equals("false"));

	}

	/**
	 * Returns true if the given token is an assignment statement of the type
	 * "variable=value", where the value is a non-negative integer.
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isAssignment(String token) {

		// The code below uses Java regular expressions. You are NOT required to
		// understand Java regular expressions, but if you are curious, see:
		// <http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html>
		//
		// Basically, this method returns true if and only if the token matches
		// the following structure:
		// one or more letters, followed by
		// an equal sign '=', followed by
		// one or more digits.

		return token.matches("[a-zA-Z]+=\\d+");
	}

	/**
	 * Given a sequence of tokens through a StringSplitter object, this method
	 * returns true if the tokens can be parsed according to the rules of the
	 * language as specified in the assignment.
	 */

	public static boolean parse(StringSplitter splitter) {

		return (splitter.hasMoreTokens() ? isValidStatement(splitter) : false);
	}

	private static boolean isValidStatement(StringSplitter splitter) {

		String token = splitter.nextToken();

		return (token.equals("if") ? isIfStatement(splitter)
				: isAssignment(token));
	}

	private static boolean isIfStatement(StringSplitter splitter) {

		boolean indeed = isBoolean(splitter.nextToken());

		if (indeed) {
			indeed = splitter.nextToken().equals("then");
		}
		if (indeed) {
			indeed = isValidStatement(splitter);
		}
		if (indeed) {
			indeed = splitter.nextToken().equals("else");
		}
		if (indeed) {
			indeed = isValidStatement(splitter);
		}
		if (indeed) {
			indeed = splitter.nextToken().equals("end");
		}

		return indeed;
	}
}