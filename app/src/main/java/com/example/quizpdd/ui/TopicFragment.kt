package com.example.quizpdd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizpdd.R
import com.example.quizpdd.databinding.FragmentTopicBinding
import com.example.quizpdd.domain.model.Topic
import com.example.quizpdd.domain.model.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicFragment : Fragment() {

    private var _binding: FragmentTopicBinding? = null
    private val binding get() = _binding!!
    private val questionGroupAdapter by lazy {
        QuestionGroupAdapter { id ->
            Toast.makeText(requireActivity(), "$id", Toast.LENGTH_SHORT).show()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.myFlow.collect {
                when (it) {
                    is State.Error -> showError(it.throwable)
                    State.Loading -> showLoading()
                    is State.Success -> showSuccess(it.data)
                }
            }
        }

        binding.recyclerViewTheme.apply {
            layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = questionGroupAdapter
        }

        binding.toggleButton.check(R.id.study_button)
        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if (isChecked) {
                when (checkedId) {
                    R.id.study_button -> {
                        Toast.makeText(group.context, "button1", Toast.LENGTH_SHORT).show()
                    }

                    R.id.training_button -> {
                        Toast.makeText(group.context, "button2", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if (group.checkedButtonId == View.NO_ID) {
                    Toast.makeText(group.context, "No aligment selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showSuccess(data: List<Topic>) {
        questionGroupAdapter.differ.submitList(data)
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(throwable: Throwable) {
        val snackbar = Snackbar.make(binding.root, "${throwable.message}", Snackbar.LENGTH_LONG)
        snackbar.setAction("Reload") {
            //TODO reload data
        }
        snackbar.show()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}