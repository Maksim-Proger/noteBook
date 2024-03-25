package project.gb.database.repository

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var db: DictionaryDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DictionaryDatabase::class.java,
            "db"
        ).build()
    }
}