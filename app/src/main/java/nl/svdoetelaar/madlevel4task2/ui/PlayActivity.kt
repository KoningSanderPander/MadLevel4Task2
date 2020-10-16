package nl.svdoetelaar.madlevel4task2.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_play.*
import nl.svdoetelaar.madlevel4task2.R
import nl.svdoetelaar.madlevel4task2.databinding.ActivityPlayBinding


class PlayActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityPlayBinding

    private var selectedMenu = R.menu.menu_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, PlayActivity::class.java))
            finish()
        }

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(selectedMenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.history -> {
                item.isVisible = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
                selectedMenu = R.menu.menu_delete
                onCreateOptionsMenu(toolbar.menu)
                navController.navigate(
                    R.id.action_playFragment_to_historyFragment
                )


                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}