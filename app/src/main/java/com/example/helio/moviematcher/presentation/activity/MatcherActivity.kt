package com.example.helio.moviematcher.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.databinding.ActivityMatcherBinding
import com.example.helio.moviematcher.presentation.fragment.ListFragment
import com.example.helio.moviematcher.presentation.fragment.MatcherFragment
import com.example.helio.moviematcher.presentation.fragment.ProfileFragment
import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

//TODO refactor rename to MainActivity
class MatcherActivity : AppCompatActivity() {

    companion object {
        val TAG_HOME = "fragment_home"
        val TAG_LIST = "fragment_list"
    }

    private val viewModel: MovieViewModel by viewModel()
    lateinit var binding: ActivityMatcherBinding
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_matcher)

        setupView()
        binding.mainContent
        goToFragment(MatcherFragment.newInstance())
        binding.matcherBtn.setOnClickListener{
           goToFragment(MatcherFragment.newInstance())
        }
        binding.profileBtn.setOnClickListener {
            goToFragment(ProfileFragment.newInstance())
        }
        binding.favBtn.setOnClickListener {
            goToFragment(ListFragment.newInstance())
        }
        binding.tvHotValue.text = page.toString()


    }

    private fun goToFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        var tag = ""
        if (fragment is MatcherFragment) tag = TAG_HOME
        if (fragment is ListFragment) tag = TAG_LIST
        ft.replace(R.id.main_content, fragment, tag)
        ft.commit()
        verifyFragment(fragment)
    }

    private fun verifyFragment(fragment: Fragment) {
    }

    private fun setupView() {
        supportActionBar?.hide()

    }
}
