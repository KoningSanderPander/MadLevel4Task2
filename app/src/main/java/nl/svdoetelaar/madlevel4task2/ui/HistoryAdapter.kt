package nl.svdoetelaar.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.svdoetelaar.madlevel4task2.R
import nl.svdoetelaar.madlevel4task2.databinding.FragmentGameHistoryBinding
import nl.svdoetelaar.madlevel4task2.databinding.FragmentHistoryBinding
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissors
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame

class HistoryAdapter(private val games: List<RockPaperScissorsGame>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FragmentGameHistoryBinding.bind(itemView)

        fun dataBind(game: RockPaperScissorsGame) {
            binding.ivComputer.setImageResource(getImageResource(game.computer))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_game_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

}