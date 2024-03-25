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

    @Query("SELECT * FROM word WHERE word_id = :wordId LIMIT 1")
    suspend fun getWordById(wordId: String): Word?

    @Query("UPDATE word SET count = :count WHERE word_id = :wordId")
    suspend fun updateWordCount(wordId: String, count: Int)
}