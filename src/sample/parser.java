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

    private TreeNode createNode(String label, String shape ,TreeNode left, TreeNode right) {
        return new TreeNode(label,shape, left, right);
    }

    private TreeNode createNode(String label,String shape) {
        return new TreeNode(label,shape);
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
            nextNode = createNode(null,"no shape", nextNode, statement);
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
                    match(TokenType.END);
                    return createNode("IF", "rectangle",condition, thenBranch);

                case REPEAT:
                    match(TokenType.REPEAT);
                    TreeNode repeatStmt = stmtSequence();
                    match(TokenType.UNTIL);
                    TreeNode untilCondition = expression();
                    return createNode("REPEAT","rectangle", repeatStmt, createNode("UNTIL","oval", untilCondition, null));
                case IDENTIFIER:
                    value= "("+currentTokenRecord.getTokenString()+")";
                    match(TokenType.IDENTIFIER);
                    match(TokenType.ASSIGN);
                    TreeNode assignmentExpr = expression();
                    return createNode("ASSIGN"+value,"rectangle", null ,assignmentExpr);
                case READ:
                    match(TokenType.READ);
                    value = "(" + currentTokenRecord.getTokenString() + ")";
                    match(TokenType.IDENTIFIER);
                    return createNode("READ"+value,"rectangle", null, null);
                case WRITE:
                    match(TokenType.WRITE);
                    TreeNode writeExpr = expression();
                    return createNode("WRITE","rectangle", null, writeExpr);
                case THEN:
                    break;
//                case END:
//                    match(TokenType.END);
//                    return createNode("END");
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
            return createNode(op, "oval",simpleExpr, rightExpr);
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
            termExpr = createNode(op,"oval" ,termExpr, rightTerm);
        }
        return termExpr;
    }

    private TreeNode term() {
        TreeNode factorExpr = factor();
        while (currentTokenRecord != null && (currentTokenRecord.getTokenType() == TokenType.MULT ||
                currentTokenRecord.getTokenType() == TokenType.DIV)) {
            String op ="op(" +currentTokenRecord.getTokenString()+")";
            match(currentTokenRecord.getTokenType());
            TreeNode rightFactor = factor();
            factorExpr = createNode(op,"oval" ,factorExpr, rightFactor);
        }
        return factorExpr;
    }
    private TreeNode factor() {
        if (currentTokenRecord != null) {
            if (currentTokenRecord.getTokenType() == TokenType.OPENBRACKET) {
                match(TokenType.OPENBRACKET);
                TreeNode expressionNode = expression();
                match(TokenType.CLOSEDBRACKET);
                return expressionNode;
            } else if (currentTokenRecord.getTokenType() == TokenType.NUMBER ||
                    currentTokenRecord.getTokenType() == TokenType.IDENTIFIER) {
                TreeNode leafNode = createNode(currentTokenRecord.getTokenType()+"("+currentTokenRecord.getTokenString()+")","oval");
                match(currentTokenRecord.getTokenType());
                return leafNode;
            } else {
                error("Unexpected token in factor: " + currentTokenRecord.getTokenType());
            }
        }
        return null;
    }

    private void match(TokenType expectedToken) {
        if (currentTokenRecord.getTokenType() == expectedToken) {
            currentTokenRecord = getNextTokenRecord();
        } else {
            error("Unexpected token: " + currentTokenRecord.getTokenType() + ". Expected: " + expectedToken);
        }
    }

    private void error(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }

//    public static void main(String[] args) {
//        String tinyCode = "{ Sample program in TINY language – computes factorial\n" +
//                "}\n" +
//                "read x; {input an integer }\n" +
//                "if 0 < x then { don’t compute if x <= 0 }\n" +
//                "fact := 1;\n" +
//                "repeat\n" +
//                "fact := fact * x;\n" +
//                "x := x - 1\n" +
//                "until x = 0;\n" +
//                "write fact { output factorial of x }\n" +
//                "end"; // Replace with your TINY code
//        Scanner scanner = new Scanner(tinyCode);
//        Queue<TokenRecord> tokenRecordsQueue = new LinkedList<>(scanner.getAllTokens());
//
//        Parser parser = new Parser(tokenRecordsQueue);
//        TreeNode parseTree = parser.parse();
//
//        // Print the parse tree
//        System.out.println("Parse tree:");
//        parseTree.printTree();
//    }


}
