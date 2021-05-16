package hu.dobszai.bookbrowse.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.base.NavigationCommand
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

abstract class BaseFragment : Fragment() {

    abstract val _viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()
        _viewModel.navigationCommand.observe(this, { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.BackTo -> findNavController().popBackStack(
                    command.destinationId, false
                )
            }
        })
    }
    abstract fun setUpRecyclerView()

    abstract fun populateList()
}