package sample;

import java.io.File;

public class Scanner {

    private String tinyCode = null;
    private int index;
    TokenRecord [] tokenRecords = null;
    File tokenFile = null;

    public Scanner(String tinyCode) {
        this.tinyCode = tinyCode.replaceAll("\\s","");
        this.index = 0;
    }

    char getNextChar(){

        if(index < tinyCode.length()){

            return tinyCode.charAt(index++);
        }
        else{
            return '\r';
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
