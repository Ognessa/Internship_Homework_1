package carrira.elan.myapplication.ui.pages.poll_third_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.databinding.FragmentPollThirdPageBinding

class PollThirdPageFragment : Fragment() {
    private val viewModel: PollThirdPageViewModel by viewModels()
    private lateinit var binding : FragmentPollThirdPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poll_third_page, container, false)
        binding.pollThirdPageViewModel = viewModel

        val context = requireContext()
        val enterAnswerModelList = JSONHelper(context).getAllQuestions().getEnterAnswerModelList()
        for(it in enterAnswerModelList){
            val textView = TextView(context)
            textView.text = it.getQuestion()
            val editText = EditText(context)
            editText.hint = context.resources.getString(R.string.hint_text)
            binding.llPollContainer.addView(textView)
            binding.llPollContainer.addView(editText)
        }

        binding.btnFinish.setOnClickListener {
            val action = PollThirdPageFragmentDirections.actionPollThirdPageFragmentToPoolResultFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}