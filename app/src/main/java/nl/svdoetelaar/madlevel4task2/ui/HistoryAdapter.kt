package nl.svdoetelaar.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.svdoetelaar.madlevel4task2.R
import nl.svdoetelaar.madlevel4task2.databinding.FragmentGameHistoryBinding
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame
import nl.svdoetelaar.madlevel4task2.model.Winner

class HistoryAdapter(private val games: List<RockPaperScissorsGame>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FragmentGameHistoryBinding.bind(itemView)

        fun dataBind(game: RockPaperScissorsGame) {
            binding.ivComputer.setImageResource(getImageResource(game.computer))
            binding.ivYou.setImageResource(getImageResource(game.player))
            binding.tvDateTime.text = game.date
            binding.tvResult.text = Winner.values()[game.winner].name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_game_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

}