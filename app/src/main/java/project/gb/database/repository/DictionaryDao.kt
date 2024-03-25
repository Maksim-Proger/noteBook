package project.gb.database.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM word")
    fun getAll(): Flow<List<Word>>

    @Insert
    suspend fun addWord(word: Word)
}