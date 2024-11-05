package com.iomp.taskmanager;

import java.util.*;

public class Response {
    String status;
    List<Task> data;
    Response(String status,List<Task> data){
        this.status=status;
        this.data=data;
    }
    public String getStatus(){
        return status;
    }
    public List<Task> getData(){
        return data;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setData(List<Task> data){
        this.data=data;
    }
}
