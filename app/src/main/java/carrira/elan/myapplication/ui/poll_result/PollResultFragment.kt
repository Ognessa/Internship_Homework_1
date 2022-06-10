package carrira.elan.myapplication.ui.poll_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.R
import carrira.elan.myapplication.databinding.FragmentPollResultBinding

class PollResultFragment : Fragment() {

    private val viewModel: PollResultViewModel by viewModels()
    private lateinit var binding : FragmentPollResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poll_result, container, false)
        binding.pollResultViewModel = viewModel

        val context = requireContext()
        val list = JSONHelper(context).getAllQuestions()
        viewModel.checkResult(list)
        viewModel.result.observe(viewLifecycleOwner) {
            binding.tvResult.text =
                String.format(context.resources.getString(R.string.poll_result), it)
        }

        binding.btnReturnToStart.setOnClickListener {
            val action = PollResultFragmentDirections.actionPoolResultFragmentToStartPollFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}