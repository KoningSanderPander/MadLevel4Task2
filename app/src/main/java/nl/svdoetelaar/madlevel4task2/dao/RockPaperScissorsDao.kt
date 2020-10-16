package nl.svdoetelaar.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nl.svdoetelaar.madlevel4task2.model.RockPaperScissorsGame

@Dao
interface RockPaperScissorsDao {

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<RockPaperScissorsGame>

    @Query("SELECT COUNT(*) FROM game WHERE winner = 0")
    suspend fun getAllPlayerWins(): Int

    @Query("SELECT COUNT(*) FROM game WHERE winner = 1")
    suspend fun getAllDraws(): Int

    @Query("SELECT COUNT(*) FROM game WHERE winner = 2")
    suspend fun getAllComputerWins(): Int

    @Insert
    suspend fun insertGame(game: RockPaperScissorsGame)

    @Query("DELETE FROM game")
    suspend fun clearGameHistory()
}