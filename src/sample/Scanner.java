package sample;

import java.io.File;

public class Scanner {

    private String tinyCode = null;
    private int index;
    TokenRecord [] tokenRecords = null;
    File tokenFile = null;

    public Scanner(String tinyCode) {
        this.tinyCode = tinyCode;
        this.index = 0;
    }

    char getNextChar(){

        if(index < tinyCode.length()){

            return tinyCode.charAt(index++);
        }
        else{
            return '\0';
        }


    }

    void ungetChar(){
        index--;
    }

    //static TokenRecord getToken(){}

    //static TokenRecord[] getALlTokens(){}


    void save(){

    }

    void print(){

    }



}
