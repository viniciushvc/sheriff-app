package com.vs.sheriff.controller.database_room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vs.sheriff.controller.database_room.entity.UserEntity;

@Dao
public interface UserDao {

    @Query("select * from user where email = :email and password = :password")
    UserEntity login(String email, String password);

    @Insert
    void insert(UserEntity productEntity);

    @Update
    void update(UserEntity productEntity);
}
