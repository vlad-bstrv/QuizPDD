package com.example.quizpdd.ui.question

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import com.example.quizpdd.R
import com.example.quizpdd.databinding.FragmentQuestionBinding
import com.example.quizpdd.domain.State
import com.example.quizpdd.domain.model.Question
import com.example.quizpdd.ui.common.UiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backTextView.setOnClickListener {
            navigateToTopicFragment(it)
        }

        fetchData()
        observe()

        binding.nextQuestionBtn.setOnClickListener {
            viewModel.nextQuestion()
        }

        binding.restartTextView.setOnClickListener {
            fetchData()
        }
    }

    private fun navigateToTopicFragment(it: View) {
        it.findNavController().navigate(R.id.action_questionFragment_to_topicFragment)
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.completionFlow.collect {
                if (it) {
                    binding.apply {
                        resultView.visibility = View.VISIBLE
                        resultTitleTextView.text = resources.getString(R.string.excellent)
                        resultTextView.text =
                            resources.getString(R.string.result, viewModel.getResult())
                    }
                }
            }
        }
    }

    private fun fetchData() {
        viewModel.getQuestion(arguments?.getInt(idKey) ?: 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showError(throwable: String) {
        val snackbar = Snackbar.make(binding.root, throwable, Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.reload)) {
            fetchData()
        }
        snackbar.show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE

    }

    private fun showSuccess(data: Question) {
        binding.questionNumberTextView.text = data.title
        binding.questionTextView.text = data.question

        if (data.imageUrl != null) {
            binding.questionImageView.visibility = View.VISIBLE
            binding.questionImageView.load(data.imageUrl)
        } else {
            binding.questionImageView.visibility = View.GONE
        }

        val answerButtons = listOf(
            binding.answerOneBtn,
            binding.answerTwoBtn,
            binding.answerThreeBtn,
            binding.answerFourBtn
        )

        reloadButtons(answerButtons)
        binding.nextQuestionBtn.isEnabled = false

        answerButtons.forEachIndexed { index, button ->
            if (index in data.answers.indices) {
                button.text = data.answers[index].answerText
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
            }

            button.setOnClickListener {
                if (data.answers[index].isCorrect) {
                    it.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                    viewModel.incrementRightAnswers()
                } else {
                    it.backgroundTintList = ColorStateList.valueOf(Color.RED)
                }
                binding.nextQuestionBtn.isEnabled = true
                enableButtons(button.id, answerButtons)
            }
        }
    }

    private fun reloadButtons(buttons: List<Button>) {
        buttons.forEach {
            it.isEnabled = true
            it.backgroundTintList =
                ColorStateList.valueOf(requireActivity().getColor(R.color.yellow_dark))
        }
    }

    private fun enableButtons(id: Int, buttons: List<Button>) {
        buttons.forEach { button ->
            if (id != button.id) {
                button.isEnabled = false
            }
        }
    }

    companion object {
        const val idKey = "ID_KEY"
    }
}