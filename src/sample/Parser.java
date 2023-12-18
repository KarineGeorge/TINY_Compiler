package sample;
import java.util.LinkedList;
import java.util.Queue;


public class Parser {
    private Queue<TokenRecord> allTokenRecord;
    private TokenRecord currentTokenRecord;
    private String errorString;
    private Boolean errorFlag;

    public Parser(Queue<TokenRecord> allTokenRecord) {
        this.allTokenRecord = new LinkedList<>(allTokenRecord);
        this.currentTokenRecord = getNextTokenRecord();
        this.errorString=null;
        this.errorFlag=false;
    }

    private TokenRecord getNextTokenRecord() {
        if (allTokenRecord.isEmpty()) {
            return new TokenRecord(TokenType.EOF, "END");
        } else {
            return allTokenRecord.poll();
        }
    }
    public String getErrorString() {
        return this.errorString;
    }
    public void  setErrorString(String S) {
        this.errorString=S;
    }
    public void setErrorFlag(boolean b){
        this.errorFlag=b;
    }
    public boolean getErrorFlag(){
        return this.errorFlag;
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
        TreeNode tr =stmtSequence();
        match(TokenType.EOF);
        return tr;
    }

    private TreeNode stmtSequence() {
        TreeNode statementNode = statement();


        while (currentTokenRecord.getTokenType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            TreeNode statement = statement();
            if (!errorFlag) {
                statementNode = createNode(null, "no shape", statementNode, statement);
            }
        }

        return statementNode;
    }

    private TreeNode statement() {
        if(!errorFlag) {
            String value = null;
            switch (currentTokenRecord.getTokenType()) {
                case SEMICOLON:
                    break;
                case IF:
                    match(TokenType.IF);
                    TreeNode condition = expression();
                    match(TokenType.THEN);
                    TreeNode thenBranch = stmtSequence();
                    match(TokenType.END);
                    return createNode("if", "rectangle", condition, thenBranch);

                case REPEAT:
                    match(TokenType.REPEAT);
                    TreeNode repeatStmt = stmtSequence();
                    match(TokenType.UNTIL);
                    TreeNode untilCondition = expression();
                    return createNode("repeat", "rectangle", repeatStmt, untilCondition);
                case IDENTIFIER:
                    value = "(" + currentTokenRecord.getTokenString() + ")";
                    match(TokenType.IDENTIFIER);
                    match(TokenType.ASSIGN);
                    TreeNode assignmentExpr = expression();
                    return createNode("assign" + value, "rectangle", null, assignmentExpr);
                case READ:
                    match(TokenType.READ);
                    value = "(" + currentTokenRecord.getTokenString() + ")";
                    match(TokenType.IDENTIFIER);
                    return createNode("read" + value, "rectangle", null, null);
                case WRITE:
                    match(TokenType.WRITE);
                    TreeNode writeExpr = expression();
                    return createNode("write", "rectangle", null, writeExpr);
                case THEN:
                    break;
                default:

                    error("Unexpected token in statement: " + currentTokenRecord.getTokenString());
            }
        }

        return null;
    }


    private TreeNode expression() {
        TreeNode simpleExpr = simpleExpression();
        if(!errorFlag){
        if (currentTokenRecord.getTokenType() == TokenType.LESSTHAN ||
                currentTokenRecord.getTokenType() == TokenType.EQUAL) {
            String op ="op(" +currentTokenRecord.getTokenString()+")";
            match( currentTokenRecord.getTokenType());
            TreeNode rightExpr = simpleExpression();
            return createNode(op, "oval",simpleExpr, rightExpr);
        }}
        return simpleExpr;
    }

    private TreeNode simpleExpression() {
        TreeNode termExpr = term();
        if(!errorFlag){
        while  (currentTokenRecord.getTokenType() == TokenType.PLUS ||
                currentTokenRecord.getTokenType() == TokenType.MINUS) {
            String op = "op("+currentTokenRecord.getTokenString()+")";
            match(currentTokenRecord.getTokenType());
            TreeNode rightTerm = term();
            termExpr = createNode(op,"oval" ,termExpr, rightTerm);
        }}
        return termExpr;
    }

    private TreeNode term() {

        TreeNode factorExpr = factor();
        if(!errorFlag){
        while (currentTokenRecord.getTokenType() == TokenType.MULT ||
                currentTokenRecord.getTokenType() == TokenType.DIV) {
            String op ="op(" +currentTokenRecord.getTokenString()+")";
            match(currentTokenRecord.getTokenType());
            TreeNode rightFactor = factor();
            factorExpr = createNode(op,"oval" ,factorExpr, rightFactor);
        }}
        return factorExpr;
    }
    private TreeNode factor() {
        if(!errorFlag) {
            if (currentTokenRecord.getTokenType() == TokenType.OPENBRACKET) {
                match(TokenType.OPENBRACKET);
                TreeNode expressionNode = expression();
                match(TokenType.CLOSEDBRACKET);
                return expressionNode;
            } else if (currentTokenRecord.getTokenType() == TokenType.NUMBER ||
                    currentTokenRecord.getTokenType() == TokenType.IDENTIFIER) {
                TreeNode leafNode;
                if (currentTokenRecord.getTokenType() == TokenType.NUMBER) {
                    leafNode = createNode("constant" + "(" + currentTokenRecord.getTokenString() + ")", "oval");
                } else {
                    leafNode = createNode("id" + "(" + currentTokenRecord.getTokenString() + ")", "oval");
                }
                match(currentTokenRecord.getTokenType());
                return leafNode;
            } else {
                error("Unexpected token in factor: " + currentTokenRecord.getTokenString());
            }
        }

        return null;
    }

    private void match(TokenType expectedToken) {
        if (currentTokenRecord.getTokenType() == expectedToken) {
            currentTokenRecord = getNextTokenRecord();
        } else {
            error("Unexpected token: " + currentTokenRecord.getTokenString() + ", Expected: " + expectedToken);
        }
    }

    private void error(String message) {
        if(!this.errorFlag){
        this.errorString=message;
        this.errorFlag=true;
        System.out.println(this.errorString);}
    }

//    public static void main(String[] args) {
//        String tinyCode = "{ Fibonacci sequence }\n" +
//                "read n;\n" +
//                "a := 0;\n" +
//                "b := 0;\n" +
//                "write a;\n" +
//                "write b;\n" +
//                "repeat\n" +
//                "  c := a *c;\n" +
//                "  write c;\n" +
//                "  a := b\n" +
//                "  b :=c\n" +
//                "until c< n"; // Replace with your TINY code
//        Scanner scanner = new Scanner(tinyCode);
//        Queue<TokenRecord> tokenRecordsQueue = new LinkedList<>(scanner.getAllTokens());
//        Parser parser = new Parser(tokenRecordsQueue);
//        TreeNode syntaxTree = parser.parse();
//
//        // Print the parse tree
//        System.out.println("syntaxTree tree:");
//        syntaxTree.printTree();
//    }


}
