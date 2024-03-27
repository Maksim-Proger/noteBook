package project.gb.database.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import project.gb.database.R
import project.gb.database.adapter.DictionaryAdapter
import project.gb.database.databinding.FragmentMainBinding
import project.gb.database.repository.App

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding : FragmentMainBinding
        get() {
            return _binding!!
        }
    private val viewModel: DictionaryViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dictionaryDao = (requireContext().applicationContext as App).db.dictionaryDao()
                return DictionaryViewModel(dictionaryDao) as T
            }
        }
    }

    private lateinit var adapter: DictionaryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DictionaryAdapter()
        binding.recyclerView.adapter = adapter

        binding.save.setOnClickListener {
            viewModel.onSave(binding.textInputEditText.text.toString())
        }

        binding.textInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.checkingEnteredText(text)
        }

        binding.clear.setOnClickListener {
            viewModel.onDelete()
        }

        print()
        checkState()
    }

    private fun checkState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    State.ERROR -> {
                        binding.save.isEnabled = false
                        binding.textInputLayout.isErrorEnabled = true
                        binding.textInputLayout.error = resources.getString(R.string.text_error)
                    }

                    State.SUCCESS -> {
                        binding.save.isEnabled = true
                        binding.textInputLayout.isErrorEnabled = false
                    }

                    State.START -> binding.save.isEnabled = false
                }
            }
        }
    }

    private fun print() {
        lifecycleScope.launch {
            viewModel.allWords.collect {list ->
                adapter.submitList(list)
            }
        }
    }
}