package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.base.NavigationCommand
import hu.dobszai.bookbrowse.databinding.FragmentBrowseBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.utils.appBarConfiguration
import hu.dobszai.bookbrowse.utils.closeKeyboard
import hu.dobszai.bookbrowse.utils.disableAppBarTitle
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

class BrowseFragment : BaseFragment(), BookListAdapter.ClickListener {

    override val _viewModel: BookViewModel by activityViewModels()
    private lateinit var binding: FragmentBrowseBinding
    private lateinit var bookAdapter: BookListAdapter

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

        binding.inToolbar.customTitle.text = getString(R.string.nav_home)

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

        setHasOptionsMenu(true)

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
                closeKeyboard(activity as AppCompatActivity)
            } else {
                Toast.makeText(context, R.string.book_title_required, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListAdapter(this)

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
        _viewModel.apply {
            totalItems.observe(viewLifecycleOwner, {
                binding.totalItems.text = String.format(
                    resources.getString(R.string.total_items),
                    it.toString(),
                )
            })

            books.observe(viewLifecycleOwner, {
                bookAdapter.setBookList(it)
            })
        }
    }

    override fun onBookClick(books: List<Book>, bookPosition: Int) {
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                BrowseFragmentDirections.actionBrowseFragmentToDetailsFragment(
                    bookPosition,
                    books.toTypedArray()
                )
            )
        )
    }
}