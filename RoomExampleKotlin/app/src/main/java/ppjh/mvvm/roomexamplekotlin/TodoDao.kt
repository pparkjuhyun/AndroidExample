package ppjh.mvvm.roomexamplekotlin

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM TODO")
    fun getAll(): List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}