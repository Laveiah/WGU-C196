package com.example.c196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.Entity.Mentor;

import java.util.List;

@Dao
public interface MentorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mentor mentor);

    @Update
    void update(Mentor mentor);

    @Delete
    void delete(Mentor mentor);

    @Query("SELECT * FROM mentors ORDER BY mentorID ASC")
    List<Mentor> getAllMentors();
}



