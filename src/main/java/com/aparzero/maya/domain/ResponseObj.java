package com.aparzero.maya.domain;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseObj {
    public String message;
    public boolean success;
    public Object data;

    public static ResponseEntity<ResponseObj> success(Object data){
        ResponseObj responseObj = new ResponseObj();
        responseObj.setSuccess(true);
        responseObj.setMessage("SUCCESS");
        responseObj.setData(data);
        return ResponseEntity.ok().body(responseObj);
    }
    public static ResponseEntity<ResponseObj> failed(String message, int status){
        ResponseObj responseObj = new ResponseObj();
        responseObj.setMessage(message);
        return ResponseEntity.status(status).body(responseObj);
    }
}