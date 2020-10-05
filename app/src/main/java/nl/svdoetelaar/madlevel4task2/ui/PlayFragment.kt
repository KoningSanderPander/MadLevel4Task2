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
import nl.svdoetelaar.madlevel4task2.R
import nl.svdoetelaar.madlevel4task2.databinding.FragmentPlayBinding
import nl.svdoetelaar.madlevel4task2.model.GameMode
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissors
import nl.svdoetelaar.madlevel4task2.model.Winner
import java.lang.Math.floorMod
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private var gameMode = GameMode.RANDOM
    private val aRPS = RockPaperScissors.values() //arrayOfRockPaperScissors (this is just shorter)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

        binding.ivRock.setOnClickListener { play(RockPaperScissors.ROCK) }
        binding.ivPaper.setOnClickListener { play(RockPaperScissors.PAPER) }
        binding.ivScissors.setOnClickListener { play(RockPaperScissors.SCISSORS) }

        binding.ivYou.setOnLongClickListener { setCheat(GameMode.PLAYER) }
        binding.tvVS.setOnLongClickListener { setCheat(GameMode.DRAW) }
        binding.ivComputer.setOnLongClickListener { setCheat(GameMode.COMPUTER) }
    }

    private fun setCheat(gameMode: GameMode): Boolean {
        this.gameMode = if (this.gameMode != gameMode) {
            gameMode
        } else {
            GameMode.RANDOM
        }
        Toast.makeText(requireContext(), "Set gamemode to ${this.gameMode}", Toast.LENGTH_SHORT).show()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
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

    @RequiresApi(Build.VERSION_CODES.N)
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

        binding.ivYou.setImageResource(getImageResource(player))
        binding.ivComputer.setImageResource(getImageResource(computer))
        binding.tvResult.setText(getResultString(winner))

        Log.i("playFragmentGame", "player: $player, computer: $computer, winner $winner")
    }

    private fun getImageResource(rps: RockPaperScissors): Int {
        return when (rps) {
            RockPaperScissors.ROCK -> R.drawable.rock
            RockPaperScissors.PAPER -> R.drawable.paper
            RockPaperScissors.SCISSORS -> R.drawable.scissors
        }
    }

    private fun getResultString(winner: Winner): Int {
        return when (winner) {
            Winner.PLAYER -> R.string.you_win
            Winner.DRAW -> R.string.draw
            Winner.COMPUTER -> R.string.computer_wins
        }
    }
}