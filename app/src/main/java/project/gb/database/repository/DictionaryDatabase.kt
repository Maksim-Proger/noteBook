package project.gb.database.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Word::class],
    version = 1
)

abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao() : DictionaryDao
}