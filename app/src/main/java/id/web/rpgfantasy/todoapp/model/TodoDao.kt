package id.web.rpgfantasy.todoapp.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE done=0 ORDER BY priority DESC")
    suspend fun selectAllUndoneTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id:Int): Todo

    @Query("UPDATE todo SET done=1 WHERE uuid=:id")
    suspend fun updateCheck(id: Int)

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid=:id" )
    suspend fun update(title:String, notes:String, priority:String, id:Int)

    @Delete
    suspend fun deleteTodo(todo:Todo)
}