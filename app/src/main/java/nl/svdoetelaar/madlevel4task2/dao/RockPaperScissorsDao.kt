package nl.svdoetelaar.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame

@Dao
interface RockPaperScissorsDao {

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<RockPaperScissorsGame>

    @Insert
    suspend fun insertGame(game: RockPaperScissorsGame)

    @Query("DELETE FROM game")
    suspend fun clearGameHistory()


}