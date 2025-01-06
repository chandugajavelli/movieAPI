package com.chandu.movie_service.exceptions;

public class InvalidDataException extends RuntimeException{
    

   public InvalidDataException(String message)
   {
    super(message);
   }
}
