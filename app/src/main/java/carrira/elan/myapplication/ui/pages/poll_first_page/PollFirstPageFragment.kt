package carrira.elan.myapplication.ui.pages.poll_first_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrira.elan.myapplication.R

class PollFirstPageFragment : Fragment() {

    companion object {
        fun newInstance() = PollFirstPageFragment()
    }

    private lateinit var viewModel: PollFirstPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_poll_first_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PollFirstPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}