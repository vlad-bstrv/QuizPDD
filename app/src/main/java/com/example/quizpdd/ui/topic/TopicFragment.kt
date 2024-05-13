package com.example.quizpdd.ui.topic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizpdd.R
import com.example.quizpdd.databinding.FragmentTopicBinding
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.ui.common.UiState
import com.example.quizpdd.ui.question.QuestionFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicFragment : Fragment() {

    private var _binding: FragmentTopicBinding? = null
    private val binding get() = _binding!!
    private val questionGroupAdapter by lazy {
        TopicAdapter { id ->
            navigateToQuestion(id)
        }
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerViewTheme.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = questionGroupAdapter
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is UiState.Error -> showError(it.message)
                    is UiState.IsLoading -> showLoading(it.isLoading)
                    is UiState.Success -> showSuccess(it.data)
                }
            }
        }
    }

    private fun showSuccess(data: List<Topic>) {
        questionGroupAdapter.differ.submitList(data)
    }

    private fun showError(throwable: String) {
        val snackbar = Snackbar.make(binding.root, throwable, Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.reload)) {
            viewModel.fetchTopics()
        }
        snackbar.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun navigateToQuestion(id: Int) {
        this.findNavController().navigate(
            R.id.action_topicFragment_to_questionFragment,
            bundleOf(QuestionFragment.idKey to id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}