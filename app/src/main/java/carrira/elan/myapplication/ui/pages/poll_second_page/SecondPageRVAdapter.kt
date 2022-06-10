package carrira.elan.myapplication.ui.pages.poll_second_page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.ui.pages.models.SeveralAnswersModel

class SecondPageRVAdapter (
    private val severalAnswersList : ArrayList<SeveralAnswersModel>,
    private val context : Context
) : RecyclerView.Adapter<SecondPageRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSeveralAnswersItemQuestion : TextView = itemView.findViewById(R.id.tv_several_answers_question)
        val rgSeveralAnswersItemItems : LinearLayout = itemView.findViewById(R.id.ll_several_answers_ans)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.several_answers_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val severalAnswersItem = severalAnswersList[position]
        val answersList = severalAnswersItem.getAnswers()
        val userAnswers = severalAnswersItem.getUserAnswers()
        holder.tvSeveralAnswersItemQuestion.text = severalAnswersItem.getQuestion()
        for(i in 0 until answersList.size){
            val rbAnswer = CheckBox(context)
            holder.rgSeveralAnswersItemItems.addView(rbAnswer)
            rbAnswer.text = answersList[i]
            if(userAnswers[i])
                rbAnswer.isChecked = true

            rbAnswer.setOnClickListener{
                userAnswers[i] = rbAnswer.isChecked
                severalAnswersItem.setUserAnswers(userAnswers)
                JSONHelper(context).updateQuestions(severalAnswersItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return severalAnswersList.size
    }
}