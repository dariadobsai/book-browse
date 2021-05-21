package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import hu.dobszai.bookbrowse.adapters.DetailListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.base.NavigationCommand
import hu.dobszai.bookbrowse.databinding.FragmentDetailsBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.DetailViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.properties.Delegates


class DetailsFragment : BaseFragment(), CardStackListener {

    private lateinit var binding: FragmentDetailsBinding

    private var argBookPosition by Delegates.notNull<Int>()
    private lateinit var argBookList: Array<Book>

    override val _viewModel: DetailViewModel by inject()

    private val detailsAdapter by lazy { DetailListAdapter() }
    private lateinit var manager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = CardStackLayoutManager(activity?.applicationContext, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)

        argBookPosition = DetailsFragmentArgs.fromBundle(requireArguments()).argBookPosition
        argBookList = DetailsFragmentArgs.fromBundle(requireArguments()).argBookList


        setUpRecyclerView()
        populateList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
    }

    override fun setUpRecyclerView() {
        binding.cardStackView.apply {
            layoutManager = manager
            adapter = detailsAdapter
        }

        manager.apply {
            setStackFrom(StackFrom.None)
            setDirections(Direction.HORIZONTAL)
        }
    }

    override fun populateList() {
        detailsAdapter.setBookList(argBookList.toList())
        binding.cardStackView.apply {
            scrollToPosition(argBookPosition)

        }
    }

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Right -> _viewModel.favoriteOrUnfavoriteBook(
                argBookList[manager.topPosition - 1],
                true
            )
            else -> _viewModel.favoriteOrUnfavoriteBook(
                argBookList[manager.topPosition - 1],
                false
            )
        }

        if (manager.topPosition == detailsAdapter.itemCount) {
            _viewModel.navigationCommand.postValue(
                NavigationCommand.Back
            )
        }
    }

    override fun onCardRewound() {
        Timber.d("onCardRewound")
    }

    override fun onCardCanceled() {
        Timber.d("onCardCanceled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("onCardAppeared")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("onCardDisappeared")
    }


    override fun onCardDragging(direction: Direction, ratio: Float) {
        Timber.d("onCardDragging")
    }

}
