package hu.dobszai.bookbrowse.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.base.NavigationCommand
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.databinding.FragmentBrowseBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.utils.appBarConfiguration
import hu.dobszai.bookbrowse.utils.disableAppBarTitle
import hu.dobszai.bookbrowse.viewmodels.BookViewModel
import kotlinx.android.synthetic.main.toolbar.view.*

class BrowseFragment : BaseFragment(), BookListAdapter.ClickListener {

    override val _viewModel: BookViewModel by activityViewModels()
    private lateinit var binding: FragmentBrowseBinding
    private lateinit var bookAdapter: BookListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBrowseBinding.inflate(inflater)

        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.inToolbar.toolbar)
            disableAppBarTitle()
        }

        binding.inToolbar.toolbar.custom_title.text = getString(R.string.nav_home)

        binding.apply {
            lifecycleOwner = this@BrowseFragment
            booksViewModel = _viewModel
        }

        searchBook()
        setUpRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(
            binding.inToolbar.toolbar,
            findNavController(),
            appBarConfiguration
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorites, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favoritesFragment) {
            _viewModel.navigationCommand.postValue(
                NavigationCommand.To(
                    BrowseFragmentDirections.actionBrowseFragmentToFavoritesFragment()
                )
            )
        }
        return super.onOptionsItemSelected(item)

    }

    private fun searchBook() {
        val inputField = binding.searchView

        binding.btnSearch.setOnClickListener {
            if (inputField.text.toString().isNotEmpty()) {
                _viewModel.findBook(inputField.text.toString())
                closeKeyboard()
            } else {
                Toast.makeText(context, R.string.book_title_required, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListAdapter(this, this, _viewModel)

        binding.listBooks.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        populateList()
    }

    override fun populateList() {
        _viewModel.totalItems.observe(viewLifecycleOwner, {
            binding.totalItems.text = it.toString()
        })
        _viewModel.books.observe(viewLifecycleOwner, {
            bookAdapter.setBookList(it)
        })
    }

    override fun onBookClick(books: List<Book>, currentBook: Int) {
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                BrowseFragmentDirections.actionBrowseFragmentToDetailsFragment(
                    currentBook,
                    books.toTypedArray()
                )
            )
        )
    }

    private fun closeKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}