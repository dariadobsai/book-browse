package hu.dobszai.bookbrowse.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    abstract val _viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()
        _viewModel.navigationCommand.observe(this, { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
                is NavigationCommand.Back -> findNavController().navigateUp()
                is NavigationCommand.BackTo -> findNavController().popBackStack(
                    command.destinationId, false
                )
            }
        })
    }

    abstract fun setUpRecyclerView()

    abstract fun populateList()
}