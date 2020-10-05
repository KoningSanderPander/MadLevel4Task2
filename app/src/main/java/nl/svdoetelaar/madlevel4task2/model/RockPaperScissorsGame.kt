package nl.svdoetelaar.madlevel4task2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class RockPaperScissorsGame(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var player: Int,

    var computer: Int,

    var winner: Int,

    var date: String
)