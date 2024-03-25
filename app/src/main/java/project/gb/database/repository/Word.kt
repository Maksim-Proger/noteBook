package project.gb.database.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word_id")
    val wordId: String,
    @ColumnInfo(name = "count")
    val count: Int
)
