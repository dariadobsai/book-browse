package hu.dobszai.bookbrowse.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

abstract class BaseFragment : Fragment() {

    val viewModel: BookViewModel by activityViewModels()

    abstract fun setUpRecyclerView()

    abstract fun populateList()
}