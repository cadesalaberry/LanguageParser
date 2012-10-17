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

		return noRecursion(splitter);

	}

	/**
	 * Checks the validity of the text without using recursion.
	 * 
	 * @param splitter
	 * @return
	 */
	public static boolean noRecursion(StringSplitter splitter) {

		return (splitter.hasMoreTokens() ? checkValidity(splitter) : false);
	}

	/**
	 * Eliminates the case in which the input is only an assignment. The passing
	 * of the stack is a bit messy, but it allowed me not checking for "" in the
	 * tokens, which can appear randomly. It could have been avoided by making
	 * the StringSplitter returning "\0" instead of "" when empty. No
	 * modification to StringSplitter were allowed though.
	 * 
	 * @param splitter
	 * @return
	 */
	private static boolean checkValidity(StringSplitter splitter) {

		StringStack stack = new StringStack();
		stack.push(splitter.nextToken());

		return (stack.peek().equals("if") ? checkIfStatement(splitter, stack)
				: isAssignment(stack.peek()));
	}

	/**
	 * Only focuses on "If Statements" and nested "If Statements". Stores every
	 * tokens in a stack until it reaches an "end". It then matches the "end" to
	 * the "if", checking if the grammar is correct on the way.
	 * 
	 * @param splitter
	 * @param stack
	 * @return 'true' is the syntax correspond to an "If Statement".
	 */
	private static boolean checkIfStatement(StringSplitter splitter,
			StringStack stack) {

		// Initialises the return value.
		boolean correct = true;

		// Works on the splitter until it is empty.
		while (splitter.hasMoreTokens()) {

			// Stacks splitter's tokens until it meets an "end".
			while (!stack.peek().equals("end")) {
				stack.push(splitter.nextToken());
			}

			// Goes down the stack to find the matching if pattern.
			// (From bottom to top though)
			if (stack.peek().equals("end")) {
				stack.pop();

				if (isAssignment(stack.peek()) || wasIfStatement(stack.peek())) {
					stack.pop();

					if (stack.peek().equals("else")) {
						stack.pop();

						if (isAssignment(stack.peek())
								|| wasIfStatement(stack.peek())) {
							stack.pop();

							if (stack.peek().equals("then")) {
								stack.pop();

								if (isBoolean(stack.peek())) {
									stack.pop();

									if (stack.peek().equals("if")) {
										stack.pop();
										stack.push("*#*#7780#*#*");
									} else
										correct = false;
								} else
									correct = false;
							} else
								correct = false;
						} else
							correct = false;
					} else
						correct = false;
				} else
					correct = false;
			} else
				correct = false;
		}

		// Once the splitter is empty, returns result.
		return correct;
	}

	/**
	 * Checks if the token correspond to an "If Statement" already checked by
	 * the algorithm. The code corresponds to the factory reset combination that
	 * has recently been exploited as a prank on the web. It has been chosen for
	 * its less than likely chance to be typed by misgard.
	 * 
	 * @param token
	 * @return
	 */
	private static boolean wasIfStatement(String token) {

		return token.equals("*#*#7780#*#*");
	}

	/**
	 * Solves the problem using recursion. It first checks if the splitter
	 * contains tokens, returning false otherwise.
	 * 
	 * @param splitter
	 * @return
	 */
	public static boolean recursion(StringSplitter splitter) {

		return (splitter.hasMoreTokens() ? isValidStatement(splitter) : false);
	}

	/**
	 * Checks if the following token(s) represent(s) either an assignment,
	 * either an if statement.
	 * 
	 * @param splitter
	 * @return
	 */
	private static boolean isValidStatement(StringSplitter splitter) {

		String token = splitter.nextToken();

		return (token.equals("if") ? isIfStatement(splitter)
				: isAssignment(token));
	}

	/**
	 * Checks if the following tokens represent a valid if statement,
	 * Thus if every tokens are in order.
	 * 
	 * @param splitter
	 * @return
	 */
	private static boolean isIfStatement(StringSplitter splitter) {

		boolean indeed = isBoolean(splitter.nextToken());

		if (indeed) {
			indeed = splitter.nextToken().equals("then")
					&& isValidStatement(splitter);
		}
		if (indeed) {
			indeed = splitter.nextToken().equals("else")
					&& isValidStatement(splitter);
		}
		if (indeed) {
			indeed = splitter.nextToken().equals("end");
		}

		return indeed;
	}
}