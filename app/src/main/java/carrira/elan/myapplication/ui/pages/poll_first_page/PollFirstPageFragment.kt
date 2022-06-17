package carrira.elan.myapplication.ui.pages.poll_first_page

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
import carrira.elan.myapplication.databinding.FragmentPollFirstPageBinding

class PollFirstPageFragment : Fragment() {

    private val viewModel: PollFirstPageViewModel by viewModels()
    private lateinit var binding : FragmentPollFirstPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poll_first_page, container, false)
        binding.pollFirstPageViewModel = viewModel

        val context = requireContext()
        val adapter = context.let{
            FirstPageRVAdapter(JSONHelper(it).getAllQuestions().getQuizModelList(), it)
        }
        binding.rvPollContainer.layoutManager = LinearLayoutManager(context)
        binding.rvPollContainer.adapter = adapter

        binding.btnNext.setOnClickListener {
            val action = PollFirstPageFragmentDirections.actionPoolFirstPageFragmentToPollSecondPageFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}