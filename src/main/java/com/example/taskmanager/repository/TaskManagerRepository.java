package com.example.taskmanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskmanager.entity.Task;

@Repository
public interface TaskManagerRepository extends JpaRepository<Task, Long>{

}