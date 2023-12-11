package sample;
import java.util.LinkedList;
import java.util.Queue;


public class Parser {
    private Queue<TokenRecord> allTokenRecord;
    private TokenRecord currentTokenRecord;

    public Parser(Queue<TokenRecord> allTokenRecord) {
        this.allTokenRecord = new LinkedList<>(allTokenRecord);
        this.currentTokenRecord = getNextTokenRecord();
    }

    private TokenRecord getNextTokenRecord() {
        return allTokenRecord.poll();
    }

    private TreeNode createNode(String label, TreeNode left, TreeNode right) {
        return new TreeNode(label, left, right);
    }

    private TreeNode createNode(String label) {
        return new TreeNode(label);
    }

    // Update the parse method to return the parse tree
    public TreeNode parse() {
        return program();
    }

    private TreeNode program() {
        return stmtSequence();
    }

    private TreeNode stmtSequence() {
        TreeNode statementNode = statement();
        TreeNode nextNode = statementNode;

        while (currentTokenRecord != null && currentTokenRecord.getTokenType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            TreeNode statement = statement();
            nextNode = createNode("", nextNode, statement);
        }

        return nextNode;
    }

    private TreeNode statement() {

        if (currentTokenRecord != null) {
            String value=null;
            switch (currentTokenRecord.getTokenType()) {
                case SEMICOLON:
                    break;
                case IF:
                    match(TokenType.IF);
                    TreeNode condition = expression();
                    match(TokenType.THEN);
                    TreeNode thenBranch = stmtSequence();
                    return createNode("IF", condition, thenBranch);
                case REPEAT:
                    match(TokenType.REPEAT);
                    TreeNode repeatStmt = stmtSequence();
                    match(TokenType.UNTIL);
                    TreeNode untilCondition = expression();
                    return createNode("REPEAT", repeatStmt, createNode("UNTIL", untilCondition, null));
                case IDENTIFIER:
                    value= "("+currentTokenRecord.getTokenString()+")";
                    match(TokenType.IDENTIFIER);
                    match(TokenType.ASSIGN);
                    TreeNode assignmentExpr = expression();
                    return createNode("ASSIGN"+value, null ,assignmentExpr);
                case READ:
                    match(TokenType.READ);
                    value = "(" + currentTokenRecord.getTokenString() + ")";
                    match(TokenType.IDENTIFIER);
                    return createNode("READ", createNode(value, null, null), null);
                case WRITE:
                    match(TokenType.WRITE);
                    TreeNode writeExpr = expression();
                    return createNode("WRITE", null, writeExpr);
                case THEN:
                    break;
                case END:
                    match(TokenType.END);
                    return createNode("END");
                default:
                    error("Unexpected token in statement: " + currentTokenRecord.getTokenType());
            }
        }
        return null;
    }


    private TreeNode expression() {
        TreeNode simpleExpr = simpleExpression();
        if (currentTokenRecord != null && (currentTokenRecord.getTokenType() == TokenType.LESSTHAN ||
                currentTokenRecord.getTokenType() == TokenType.EQUAL)) {
            String op ="op(" +currentTokenRecord.getTokenString()+")";
            match( currentTokenRecord.getTokenType());
            TreeNode rightExpr = simpleExpression();
            return createNode(op, simpleExpr, rightExpr);
        }
        return simpleExpr;
    }

    private TreeNode simpleExpression() {
        TreeNode termExpr = term();
        while (currentTokenRecord != null && (currentTokenRecord.getTokenType() == TokenType.PLUS ||
                currentTokenRecord.getTokenType() == TokenType.MINUS)) {
            String op = "OP("+currentTokenRecord.getTokenString()+")";
            match(currentTokenRecord.getTokenType());
            TreeNode rightTerm = term();
            termExpr = createNode(op, termExpr, rightTerm);
        }
        return termExpr;
    }

    private TreeNode term() {
        TreeNode factorExpr = factor();
        while (currentTokenRecord != null && (currentTokenRecord.getTokenType() == TokenType.MULT ||
                currentTokenRecord.getTokenType() == TokenType.DIV)) {
            TokenType op = currentTokenRecord.getTokenType();
            match(op);
            TreeNode rightFactor = factor();
            factorExpr = createNode(op.toString(), factorExpr, rightFactor);
        }
        return factorExpr;
    }


}