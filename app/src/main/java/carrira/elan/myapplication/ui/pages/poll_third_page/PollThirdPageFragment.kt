package carrira.elan.myapplication.ui.pages.poll_third_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        val adapter = context.let{
            ThirdPageRVAdapter(JSONHelper(it).getAllQuestions().getEnterAnswerModelList(), it)
        }
        binding.rvPollContainer.layoutManager = LinearLayoutManager(context)
        binding.rvPollContainer.adapter = adapter

        binding.btnFinish.setOnClickListener {
            val action = PollThirdPageFragmentDirections.actionPollThirdPageFragmentToPoolResultFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}