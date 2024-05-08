package com.example.quizpdd.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import coil.load
import com.example.quizpdd.R
import com.example.quizpdd.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.textView.text = arguments?.getInt(idKey).toString()
        binding.backTextView.setOnClickListener {
            it.findNavController().navigate(R.id.action_questionFragment_to_topicFragment)
        }

        binding.questionImageView.load("https://raw.githubusercontent.com/etspring/pdd_russia/master/images/C_D/011505ad4880a138cea38e6937815016.jpg")
    }

    companion object {
        const val idKey = "ID_KEY"
    }
}