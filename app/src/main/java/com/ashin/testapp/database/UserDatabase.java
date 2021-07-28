package com.ashin.testapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ashin.testapp.dao.UserDao;
import com.ashin.testapp.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

}