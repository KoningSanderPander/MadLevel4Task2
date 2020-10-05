package nl.svdoetelaar.madlevel4task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.svdoetelaar.madlevel4task2.databinding.FragmentHistoryBinding
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame
import nl.svdoetelaar.madlevel4task2.repository.RockPaperScissorsRepository

class HistoryFragment : Fragment() {

    private val games = arrayListOf<RockPaperScissorsGame>()
    private val historyAdapter: HistoryAdapter(games)

    private lateinit var rockPaperScissorsRepository: RockPaperScissorsRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rockPaperScissorsRepository = RockPaperScissorsRepository(requireContext())
        getGamesFromDatabase()

        initRv()
    }

    private fun initRv() {
        viewManager = LinearLayoutManager(activity)
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = historyAdapter
        }
    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            this@HistoryFragment.historyAdapter.notifyDataSetChanged()
        }
    }
}