package project.gb.database.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.gb.database.repository.DictionaryDao
import project.gb.database.repository.Word

class DictionaryViewModel(private val dictionaryDao: DictionaryDao) : ViewModel() {

    val allWords = this.dictionaryDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onSave(userWord: String) {
        viewModelScope.launch {
            dictionaryDao.addWord(Word(userWord, 1))
        }
    }
}