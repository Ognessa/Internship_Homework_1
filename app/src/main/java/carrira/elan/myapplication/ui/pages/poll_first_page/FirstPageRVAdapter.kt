package carrira.elan.myapplication.ui.pages.poll_first_page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.ui.pages.models.QuizModel

class FirstPageRVAdapter(
    private val quizList : ArrayList<QuizModel>,
    private val context : Context) :
    RecyclerView.Adapter<FirstPageRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuizQuestion : TextView = itemView.findViewById(R.id.tv_quiz_question)
        val rgQuizAnswerItems : RadioGroup = itemView.findViewById(R.id.rg_quiz_answer_items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quizItem = quizList[position]
        val answersList = quizItem.getAnswers()

        holder.tvQuizQuestion.text = quizItem.getQuestion()
        for(i in 0 until answersList.size){
            val rbAnswer = RadioButton(context)
            holder.rgQuizAnswerItems.addView(rbAnswer)
            rbAnswer.text = answersList[i]

            if(quizItem.getUserAnswer()==i)
                rbAnswer.isChecked = true

            rbAnswer.setOnClickListener {
                quizItem.setUserAnswer(i)
                JSONHelper(context).updateQuestions(quizItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}