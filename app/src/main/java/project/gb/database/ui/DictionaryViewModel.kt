package project.gb.database.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.gb.database.repository.DictionaryDao
import project.gb.database.repository.Word

class DictionaryViewModel(private val dictionaryDao: DictionaryDao) : ViewModel() {

    private val _state = MutableStateFlow(State.START)
    val state = _state.asStateFlow()

    val allWords = this.dictionaryDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onSave(userWord: String) {
        viewModelScope.launch {
            val existingWord = dictionaryDao.getWordById(userWord)
            if (existingWord != null) {
                dictionaryDao.updateWordCount(existingWord.wordId, existingWord.count + 1)
            } else {
                dictionaryDao.addWord(Word(userWord, 1))
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            dictionaryDao.delete()
        }
    }

    fun checkingEnteredText(text: CharSequence?) {
        if (!text.isNullOrEmpty() && text.matches(Regex("""^[А-Яа-я\-]{3,}${'$'}""")))
            _state.value = State.SUCCESS
        else _state.value = State.ERROR
    }

}