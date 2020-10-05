package nl.svdoetelaar.madlevel4task2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.svdoetelaar.madlevel4task2.databinding.FragmentPlayBinding
import nl.svdoetelaar.madlevel4task2.model.GameMode
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissors
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private var mode = GameMode.RANDOM
    private val rockPaperScissorsArray = RockPaperScissors.values()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

        binding.ivRock.setOnClickListener { play(RockPaperScissors.ROCK) }
        binding.ivPaper.setOnClickListener { play(RockPaperScissors.PAPER) }
        binding.ivScissors.setOnClickListener { play(RockPaperScissors.SCISSORS) }


    }

    private fun play(playerRPS: RockPaperScissors) {
        when (mode) {
            GameMode.RANDOM -> {
                decideWinner(
                    playerRPS,
                    rockPaperScissorsArray[Random.nextInt(rockPaperScissorsArray.size)]
                )
            }

            GameMode.PLAYER -> {
                decideWinner(
                    playerRPS,
                    rockPaperScissorsArray[(rockPaperScissorsArray.indexOf(playerRPS) - 1 + rockPaperScissorsArray.size) % rockPaperScissorsArray.size]
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
                    rockPaperScissorsArray[(rockPaperScissorsArray.indexOf(playerRPS) + 1) % rockPaperScissorsArray.size]
                )
            }
        }
    }

    private fun decideWinner(player: RockPaperScissors, computer: RockPaperScissors) {
        Log.i("playFragment", "player: $player, computer: $computer")
    }
}