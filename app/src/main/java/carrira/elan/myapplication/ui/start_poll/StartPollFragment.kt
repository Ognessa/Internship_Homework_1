package carrira.elan.myapplication.ui.start_poll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import carrira.elan.myapplication.R
import carrira.elan.myapplication.databinding.FragmentStartPollBinding

class StartPollFragment : Fragment() {

    private val viewModel: StartPollViewModel by viewModels()
    private lateinit var binding : FragmentStartPollBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_start_poll, container, false)
        binding.startPollViewModel = viewModel

        //Navigate to PollFragment
        binding.btnStartPoll.setOnClickListener{
            val action = StartPollFragmentDirections.actionStartPollFragmentToPoolFragment()
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

}