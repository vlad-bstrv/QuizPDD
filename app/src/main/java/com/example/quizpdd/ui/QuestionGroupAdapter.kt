package com.example.quizpdd.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quizpdd.databinding.ViewholderQuestionBinding
import com.example.quizpdd.domain.model.Topic

class QuestionGroupAdapter(
    private val onItemClickCallback: (Int) -> Unit
): RecyclerView.Adapter<QuestionGroupAdapter.ViewHolder>() {

    private lateinit var binding: ViewholderQuestionBinding

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderQuestionBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ViewholderQuestionBinding.bind(holder.itemView)
        binding.apply {
            titleTextView.text = differ.currentList[position].title
            progressIndicator.max = differ.currentList[position].allQuestion
            progressIndicator.progress = differ.currentList[position].progress
            progressTextView.text = "${differ.currentList[position].progress} / ${differ.currentList[position].allQuestion}"
        }

        binding.continueBtn.setOnClickListener {
            onItemClickCallback(differ.currentList[position].id)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}