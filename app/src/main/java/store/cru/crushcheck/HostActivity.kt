package store.cru.crushcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import store.cru.crushcheck.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHostBinding
    lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toogle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val likedFragment = LikedFragment()
        val messageFragment = MessageFragment()
        val profileFragment = ProfileFragment()
        val homeFragment = HomeFragment()

        setCurrentFragment(homeFragment)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miLikeList -> setCurrentFragment(likedFragment)
                R.id.miMessages -> setCurrentFragment(messageFragment)
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(frag: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,frag)
            commit()
        }
    }
}