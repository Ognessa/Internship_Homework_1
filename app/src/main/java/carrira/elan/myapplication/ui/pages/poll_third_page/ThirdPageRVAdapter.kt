package carrira.elan.myapplication.ui.pages.poll_third_page

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.ui.pages.models.EnterAnswerModel

class ThirdPageRVAdapter (
    private val enterAnswerList : ArrayList<EnterAnswerModel>,
    private val context : Context
) : RecyclerView.Adapter<ThirdPageRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEnterAnswerQuestion : TextView = itemView.findViewById(R.id.tv_enter_answer_question)
        val etEnterAnswer : EditText = itemView.findViewById(R.id.et_enter_answer)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.enter_answer_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val enterAnswerItem = enterAnswerList[position]
        holder.tvEnterAnswerQuestion.text = enterAnswerItem.getQuestion()
        holder.etEnterAnswer.text = enterAnswerItem.getUserAnswer().toEditable()
        holder.etEnterAnswer.addTextChangedListener {
            enterAnswerItem.setUserAnswer(it.toString())
            JSONHelper(context).updateQuestions(enterAnswerItem)}
    }

    override fun getItemCount(): Int {
        return enterAnswerList.size
    }

    private fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)
}