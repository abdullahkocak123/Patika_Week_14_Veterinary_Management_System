package dev.patika.veterinary.core.utils;

import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;

public class ResultHelper {

    public static <T>ResultData<T> createdData(T data){
        return new ResultData<>(true, Msg.CREATED, "201", data);
    }

    public static <T> ResultData<T> validateErrorData(T data){
        return new ResultData<>(false, Msg.VALIDATE_ERROR, "400", data);
    }

    public static <T>ResultData<T> successData(T data){
        return new ResultData<>(true, Msg.OK, "200", data);
    }

    public static Result success(){
        return new Result(true,Msg.OK,"200");
    }

    public static Result notFoundError (String msg){
        return new Result(false, msg, "404");
    }

//    public static Result notFoundErrorById (long id){
//        return new Result(false, id+Msg.NOT_FOUND_BY_ID, "404");
//    }
}
