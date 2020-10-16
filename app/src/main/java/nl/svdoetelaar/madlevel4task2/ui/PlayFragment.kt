package nl.svdoetelaar.madlevel4task2.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.svdoetelaar.madlevel4task2.R
import nl.svdoetelaar.madlevel4task2.databinding.FragmentPlayBinding
import nl.svdoetelaar.madlevel4task2.model.GameMode
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissors
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame
import nl.svdoetelaar.madlevel4task2.model.Winner
import nl.svdoetelaar.madlevel4task2.repository.RockPaperScissorsRepository
import java.lang.Math.floorMod
import java.time.LocalDateTime.now
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private var gameMode = GameMode.RANDOM
    private val aRPS = RockPaperScissors.values() //arrayOfRockPaperScissors (this is just shorter)

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var rockPaperScissorsRepository: RockPaperScissorsRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rockPaperScissorsRepository = RockPaperScissorsRepository(requireContext())

        binding.ivRock.setOnClickListener { play(RockPaperScissors.ROCK) }
        binding.ivPaper.setOnClickListener { play(RockPaperScissors.PAPER) }
        binding.ivScissors.setOnClickListener { play(RockPaperScissors.SCISSORS) }

        binding.ivYou.setOnLongClickListener { setCheat(GameMode.PLAYER) }
        binding.tvVS.setOnLongClickListener { setCheat(GameMode.DRAW) }
        binding.ivComputer.setOnLongClickListener { setCheat(GameMode.COMPUTER) }

        updateStats()
    }

    private fun setCheat(gameMode: GameMode): Boolean {
        this.gameMode = if (this.gameMode != gameMode) {
            gameMode
        } else {
            GameMode.RANDOM
        }
        Toast.makeText(requireContext(), "Set gamemode to ${this.gameMode}", Toast.LENGTH_SHORT)
            .show()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun play(playerRPS: RockPaperScissors) {
        when (gameMode) {
            GameMode.RANDOM -> {
                decideWinner(
                    playerRPS,
                    aRPS[Random.nextInt(aRPS.size)]
                )
            }

            GameMode.PLAYER -> {
                decideWinner(
                    playerRPS,
                    aRPS[floorMod((playerRPS.ordinal - 1), aRPS.size)]
                )
            }

            GameMode.DRAW -> {
                decideWinner(
                    playerRPS,
                    playerRPS
                )
            }

            GameMode.COMPUTER -> {
                decideWinner(
                    playerRPS,
                    aRPS[floorMod((playerRPS.ordinal + 1), aRPS.size)]
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decideWinner(player: RockPaperScissors, computer: RockPaperScissors) {
        val winner = when {
            player == computer -> {
                Winner.DRAW
            }
            floorMod((player.ordinal - 1), aRPS.size) == computer.ordinal -> {
                Winner.PLAYER
            }
            else -> {
                Winner.COMPUTER
            }
        }

        binding.ivYou.setImageResource(getImageResource(player.ordinal))
        binding.ivComputer.setImageResource(getImageResource(computer.ordinal))
        binding.tvResult.setText(getResultString(winner.ordinal))

        Log.i("playFragmentGame", "player: $player, computer: $computer, winner $winner")

        val game = RockPaperScissorsGame(
            player = player.ordinal,
            computer = computer.ordinal,
            winner = winner.ordinal,
            date = now().toString()
        )

        mainScope.launch {
            withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.insertGame(game)
            }
        }

        updateStats()
    }

    private fun updateStats() {

        mainScope.launch {
            val win = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllPlayerWins()
            }
            val draw = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllDraws()
            }
            val lose = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllComputerWins()
            }
            binding.tvStatistics.text = getString(
                R.string.statistics,
                win,
                draw,
                lose
            )

        }


    }
}

fun getImageResource(rps: Int): Int {
    return when (RockPaperScissors.values()[rps]) {
        RockPaperScissors.ROCK -> R.drawable.rock
        RockPaperScissors.PAPER -> R.drawable.paper
        RockPaperScissors.SCISSORS -> R.drawable.scissors
    }
}

private fun getResultString(winner: Int): Int {
    return when (Winner.values()[winner]) {
        Winner.PLAYER -> R.string.you_win
        Winner.DRAW -> R.string.draw
        Winner.COMPUTER -> R.string.computer_wins
    }
}