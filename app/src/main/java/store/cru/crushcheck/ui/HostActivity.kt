package store.cru.crushcheck.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import store.cru.crushcheck.*
import store.cru.crushcheck.databinding.ActivityHostBinding
import store.cru.crushcheck.firebase.FirebaseSource
import store.cru.crushcheck.repository.UserRepository
import store.cru.crushcheck.ui.fragments.*

class HostActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHostBinding
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var viewModel: FriendsViewModel
    private val source = FirebaseSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepository(source)
        val viewModerProviderFactory = FriendsViewModelProviderFactory(userRepository)
        viewModel = ViewModelProvider(this,viewModerProviderFactory).get(FriendsViewModel::class.java)

        toogle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
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
        val headerView = binding.navView.getHeaderView(0)
        headerView.findViewById<ConstraintLayout>(R.id.navHead).setOnClickListener {
            setCurrentFragment(profileFragment)
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