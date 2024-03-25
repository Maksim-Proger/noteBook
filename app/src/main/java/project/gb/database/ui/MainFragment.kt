package project.gb.database.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputEditText.setOnClickListener {
            viewModel.onSave(binding.textInputEditText.text.toString())
        }
    }

    private fun print() {
        lifecycleScope.launch {
            viewModel.allWords.collect {list ->

            }
        }
    }
}