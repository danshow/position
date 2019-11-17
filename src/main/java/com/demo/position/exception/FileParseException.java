package com.demo.position.exception;

public class FileParseException  extends RuntimeException{
    public FileParseException(){
        super();
    }
    public FileParseException(String msg){
        super(msg);
    }
}
