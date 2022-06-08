package carrira.elan.myapplication.ui.pages.poll_second_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.databinding.FragmentPollSecondPageBinding

class PollSecondPageFragment : Fragment() {
    private val viewModel: PollSecondPageViewModel by viewModels()
    private lateinit var binding : FragmentPollSecondPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poll_second_page, container, false)

        val context = requireContext()
        val severalAnswersModelList = JSONHelper(context).getAllQuestions().getSeveralAnswersModelList()
        for(it in severalAnswersModelList){
            val textView = TextView(context)
            textView.text = it.getQuestion()
            binding.llPollContainer.addView(textView)
            binding.llPollContainer.addView(viewModel.createRadioGroup(context, it))
        }

        binding.btnNext.setOnClickListener {
            val action = PollSecondPageFragmentDirections.actionPollSecondPageFragmentToPollThirdPageFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}