package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.databinding.FragmentBrowseBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.utils.appBarConfiguration
import hu.dobszai.bookbrowse.utils.disableAppBarTitle

class BrowseFragment : BaseFragment(), BookListAdapter.ClickListener {

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
            setSupportActionBar(binding.toolbar)
            disableAppBarTitle()
        }

        binding.apply {
            lifecycleOwner = this@BrowseFragment
            booksViewModel = viewModel
        }

        searchBook()
        setUpRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(
            binding.toolbar,
            findNavController(),
            appBarConfiguration
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorites, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return view?.let {
            NavigationUI.onNavDestinationSelected(
                item,
                it.findNavController()
            )
        } == true || super.onOptionsItemSelected(item)
    }

    private fun searchBook() {
        binding.btnSearch.setOnClickListener {
            if (binding.searchView.text.toString().isNotEmpty()) {
                viewModel.findBook(binding.searchView.text.toString())
            } else {
                Toast.makeText(context, R.string.book_title_required, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListAdapter(this, this, viewModel)

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
        viewModel.totalItems.observe(viewLifecycleOwner, {
            binding.totalItems.text = it.toString()
        })
        viewModel.books.observe(viewLifecycleOwner, {
            bookAdapter.setBookList(it)
        })
    }

    override fun onBookClick(book: Book) {
        Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show()
    }
}