package sample;

import java.io.File;
import java.util.Arrays;
import java.io.File;
import java.util.Queue;
import java.util.LinkedList;

public class Scanner {


    private String tinyCode = null;
    private int index;
    private StateType state;
    private boolean save;
    private static final int MAXTOKENLEN = 100;
    char[] tokenString = new char[MAXTOKENLEN];

    File tokenFile = null;

    public Scanner(String tinyCode) {
        this.tinyCode = tinyCode;
        this.index = 0;

    }

    char getNextChar() {

        if (index < tinyCode.length()) {

            return tinyCode.charAt(index++);
        } else {
            return '\0';
        }


    }

    void ungetChar() {
        index--;
    }

    private TokenType reservedLookup(String tokenString) {
        switch (tokenString) {
            case "then":
                return TokenType.THEN;
            case "repeat":
                return TokenType.REPEAT;
            case "if":
                return TokenType.IF;
            case "end":
                return TokenType.END;
            case "until":
                return TokenType.UNTIL;
            case "read":
                return TokenType.READ;
            case "write":
                return TokenType.WRITE;
            default:
                return TokenType.IDENTIFIER; // If not a reserved word, treat it as an identifier
        }
    }


    public TokenRecord getToken() {
        int tokenStringIndex = 0;
        TokenType currentToken = TokenType.None;
        StateType state = StateType.START;
        boolean save;
        char c;
        String tokenStr= "/0";
        while (state != StateType.DONE) {
            c = getNextChar();
            save = true;
            switch (state) {
                case START:
                    if (Character.isDigit(c))
                        state = StateType.INNUM;
                    else if (Character.isLetter(c))
                        state = StateType.INID;
                    else if (c == ':')
                        state = StateType.INASSIGN;
                    else if (c == ' ' || c == '\t' || c == '\n')
                        save = false;
                    else if (c == '{') {
                        save = false;
                        state = StateType.INCOMMENT;
                    } else {
                        state = StateType.DONE;
                        currentToken = switch (c) {
                            case '\0' -> {
                                save = false;
                                yield TokenType.EOF;
                            }
                            case '=' -> TokenType.EQUAL;
                            case '<' -> TokenType.LESSTHAN;
                            case '+' -> TokenType.PLUS;
                            case '-' -> TokenType.MINUS;
                            case '*' -> TokenType.MULT;
                            case '/' -> TokenType.DIV;
                            case '(' -> TokenType.OPENBRACKET;
                            case ')' -> TokenType.CLOSEDBRACKET;
                            case ';' -> TokenType.SEMICOLON;
                            default -> TokenType.ERROR;
                        };
                    }
                    break;
                case INCOMMENT:
                    save = false;
                    if (c == '\0') {
                        state = StateType.DONE;
                        currentToken = TokenType.EOF;
                    } else if (c == '}')
                        state = StateType.START;
                    break;
                case INASSIGN:
                    state = StateType.DONE;
                    if (c == '=')
                        currentToken = TokenType.ASSIGN;
                    else {
                        ungetChar();
                        save = false;
                        currentToken = TokenType.ERROR;
                    }
                    break;
                case INNUM:
                    if (!Character.isDigit(c)) {
                        if (Character.isLetter(c)){
                            while (Character.isLetter(c)||Character.isDigit(c)){
                                if ( tokenStringIndex < MAXTOKENLEN) {
                                    tokenString[tokenStringIndex++] =  c;
                                }
                               c= getNextChar();
                            }
                            ungetChar();
                            save=false;
                            state=StateType.DONE;
                            currentToken=TokenType.ERROR;
                        }
                        else{
                        ungetChar();
                        save = false;
                        state = StateType.DONE;
                        currentToken = TokenType.NUMBER;
                    } }
                    break;
                case INID:
                    if (!Character.isLetter(c)) {
                        if (Character.isDigit(c)){
                            while (Character.isLetter(c)||Character.isDigit(c)){
                                if ( tokenStringIndex < MAXTOKENLEN) {
                                    tokenString[tokenStringIndex++] =  c;
                                }
                                c= getNextChar();
                            }
                            ungetChar();
                            save=false;
                            state=StateType.DONE;
                            currentToken=TokenType.ERROR;
                        }
                        else{
                            ungetChar();
                            save = false;
                            state = StateType.DONE;
                            currentToken = TokenType.IDENTIFIER;
                        } }
                    break;

                default:
                    state = StateType.DONE;
                    currentToken = TokenType.ERROR;
                    break;
            }
            if (save && tokenStringIndex < MAXTOKENLEN) {
                tokenString[tokenStringIndex++] =  c;
            }
        }

            tokenStr = new String(tokenString, 0, tokenStringIndex);
            if (currentToken == TokenType.IDENTIFIER) {
                currentToken = reservedLookup(tokenStr);

            }

        return new TokenRecord(currentToken,tokenStr);
    }
    public Queue<TokenRecord> getAllTokens() {
        TokenRecord tokenRecord;
        Queue<TokenRecord> tokenRecordsQueue = new LinkedList<>();

        do {
            tokenRecord = getToken();

            if (tokenRecord.getTokenType() != TokenType.EOF ) {
                tokenRecordsQueue.offer(tokenRecord);
            }
        } while (tokenRecord.getTokenType() != TokenType.EOF && tokenRecord.getTokenType() != TokenType.ERROR);

        return tokenRecordsQueue;
    }



//    public static void main(String[] args) {
//        // Replace this with your Tiny code input
//        String tinyCode = "if Xyu1 :=  2H 2 ; if then <= sj ";
//
//        // Create a Scanner instance with the input string
//        Scanner x = new Scanner(tinyCode);
//
//        // Call getAllTokens to collect and display all tokens
//        Queue<TokenRecord> tokenQueue = x.getAllTokens();
//
//        // Display the collected tokens
//        while (!tokenQueue.isEmpty()) {
//            TokenRecord token = tokenQueue.poll();
//            System.out.println("Token Type: " + token.getTokenType() + ", Token Value: " + token.getTokenString());
// }
// }


//    void save(){
//
//    }
//
//    void print(){
//
//    }



}
