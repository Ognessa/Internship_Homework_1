package carrira.elan.myapplication.ui.poll_result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrira.elan.myapplication.R

class PollResultFragment : Fragment() {

    companion object {
        fun newInstance() = PollResultFragment()
    }

    private lateinit var viewModel: PollResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_poll_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PollResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}