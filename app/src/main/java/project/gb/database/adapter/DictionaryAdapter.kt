package project.gb.database.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.gb.database.R
import project.gb.database.repository.Word

import androidx.recyclerview.widget.ListAdapter

class DictionaryAdapter : ListAdapter<Word, DictionaryAdapter.WordViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_for_recycler_view, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentItem = getItem(position) // Метод getItem() используется для получения элемента списка по позиции
        holder.bind(currentItem)
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordTextView: TextView = itemView.findViewById(R.id.field_word)
        private val countTextView: TextView = itemView.findViewById(R.id.field_count)

        fun bind(word: Word) {
            wordTextView.text = word.wordId
            countTextView.text = word.count.toString()
        }
    }
}



