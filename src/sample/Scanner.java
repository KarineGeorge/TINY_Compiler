package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private StringBuilder records = new StringBuilder();

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
                        if(c!='\0')
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
                            if(c!='\0')
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
                            if(c != '\0')
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

    void save(){
        tokenFile = new File("Token File");
        try {
            FileWriter myWriter = new FileWriter(tokenFile);
            myWriter.write(records.toString());
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String print(Queue<TokenRecord> tokenqueue){
        String header = String.format("%-20s %-20s","Token Value", "Token Type") + "\n";
        records.append(header);

        while (!tokenqueue.isEmpty()) {
            TokenRecord token = tokenqueue.poll();
            if(token.getTokenType().toString().equals("ERROR")){
                //records.setLength(0);
                records.append("\nSYNTAX ERROR: " + "\"" +token.getTokenString()+ "\"" + " is an illegal sequence of characters");
            }
            else{
                String Line = String.format("%-20s %-20s",token.getTokenString(),token.getTokenType());
                records.append(Line+"\n");
            }
        }

        return records.toString();
    }

}
