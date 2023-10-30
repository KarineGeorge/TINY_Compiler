package sample;

import java.io.File;
import java.util.Arrays;

public class Scanner {


    private String tinyCode = null;
    private int index;
    private StateType state;
    private boolean save;
    private static final int MAXTOKENLEN = 100;
    private static final int MAXTOKENRECORDLEN = 100;
    char[] tokenString = new char[MAXTOKENLEN];
    TokenRecord[] tokenRecords = null;

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
                        ungetChar();
                        save = false;
                        state = StateType.DONE;
                        currentToken = TokenType.NUMBER;
                    }
                    break;
                case INID:
                    if (!Character.isLetter(c)) {
                        ungetChar();
                        save = false;
                        state = StateType.DONE;
                        currentToken = TokenType.IDENTIFIER;
                    }
                    break;
                case DONE:
                default:
                    state = StateType.DONE;
                    currentToken = TokenType.ERROR;
                    break;
            }
            if (save && tokenStringIndex < MAXTOKENLEN) {
                tokenString[tokenStringIndex++] =  c;
            }
            if (state == StateType.DONE) {
                tokenStr = new String(tokenString, 0, tokenStringIndex);
                if (currentToken == TokenType.IDENTIFIER) {
                    currentToken = reservedLookup(tokenStr);

                }
            }
        }
        return new TokenRecord(currentToken,tokenStr);
    }



//    public static void main(String[] args) {
//        // Replace this with your Tiny code input
//        String tinyCode = "if 123X :=  2H 2 ; if then <= sj ";
//
//
//
//        // Call getAllTokens to collect and display all tokens
//        Scanner x = new Scanner(tinyCode);
//
//        TokenRecord[] tokens = new TokenRecord[MAXTOKENRECORDLEN];
//        tokens = x.getALlTokens();
//   }


//    void save(){
//
//    }
//
//    void print(){
//
//    }



}
