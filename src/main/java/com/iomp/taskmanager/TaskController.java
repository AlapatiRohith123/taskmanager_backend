package com.iomp.taskmanager;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Adjust origin based on frontend location
public class TaskController {
    boolean backtrack(HashMap<Integer,List<Integer>> hm,HashMap<Integer,Task> taskinf,HashMap<Integer,Integer> indegree,LocalDate curdate,HashSet<Integer> hs,List<Task> res){

        if(hs.isEmpty()) return true;
        for(int i:new ArrayList<>(hs)){
            if(curdate.plusDays(taskinf.get(i).getDaysRequired()).compareTo(LocalDate.parse(taskinf.get(i).getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))<=0){
                hs.remove(i);
                curdate=curdate.plusDays(taskinf.get(i).getDaysRequired());
                for(int j:hm.get(i)){
                    indegree.put(j,indegree.get(j)-1);
                    if(indegree.get(j)==0) hs.add(j);
                }
                res.add(taskinf.get(i));
                if(backtrack(hm,taskinf,indegree,curdate,hs,res)) return true;
                hs.add(i);
                curdate=curdate.minusDays(taskinf.get(i).getDaysRequired());
                for(int j:hm.get(i)){
                    if(indegree.get(j)==0) hs.remove(j);
                    indegree.put(j,indegree.get(j)+1);
                }
                res.remove(res.size() - 1);
            }
        }
        return false;
    }
    @PostMapping
    public Response receiveTasks(@RequestBody List<Task> tasks) {
        tasks.forEach(task -> {
            // Process each task
            System.out.println("Received task: " + task.getDependencies());
        });

        HashMap<Integer,List<Integer>> hm=new HashMap<>();
        HashMap<Integer,Integer> indegree=new HashMap<>();
        for(Task i:tasks){
            hm.put(i.getTaskId(),new ArrayList<>());
        }
        for(Task i:tasks){
            for(int j:i.getDependencies()) {
                List<Integer> temp = hm.get(j);
                temp.add(i.getTaskId());
                hm.put(j, temp);
            }
            indegree.put(i.getTaskId(),i.getDependencies().size());
        }
        HashSet<Integer> hs=new HashSet<>();
        for(int i:indegree.keySet()){
            if(indegree.get(i)==0) hs.add(i);
        }
        HashMap<Integer,Task> taskinf=new HashMap<>();
        for(Task i:tasks){
            taskinf.put(i.getTaskId(),i);
        }
        LocalDate curdate=LocalDate.now();
        List<Task> res=new ArrayList<>();
        boolean result=backtrack(hm,taskinf,indegree,curdate,hs,res);
        if(result && res.size()==tasks.size()){
            return new Response("Success",res);
        }
        if(!result){
            return  new Response("All the tasks can't be completed within the deadline",null);
        }
        return new Response("There is a circular dependency (Ex:a depends on b and b depends on a)",null);
    }
}
