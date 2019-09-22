package com.vs.sheriff.controller.database_room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vs.sheriff.controller.database_room.dao.UserDao;
import com.vs.sheriff.controller.database_room.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 20)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}