package com.crestinfosystems_jinay.trello.HomePage.subScreens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crestinfosystems_jinay.trello.MainActivity
import com.crestinfosystems_jinay.trello.R
import com.crestinfosystems_jinay.trello.adapter.SettingTabAdapter
import com.crestinfosystems_jinay.trello.data.SettinsTilesFilelds
import com.crestinfosystems_jinay.trello.databinding.FragmentSettingTabBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class Setting_tab : Fragment() {

    private var binding: FragmentSettingTabBinding? = null
    private var settingTileField: List<SettinsTilesFilelds> =
        listOf(
            SettinsTilesFilelds(id = 0,
                title = "Profile",
                icon = R.drawable.ic_setting_profile,
                {}),
            SettinsTilesFilelds(
                id = 1,
                title = "Conversation",
                icon = R.drawable.ic_setting_conversation,
                {}),
            SettinsTilesFilelds(
                id = 2,
                title = "Projects",
                icon = R.drawable.ic_setting_project,
                {}),
            SettinsTilesFilelds(
                id = 3,
                title = "Terms and Conditions",
                icon = R.drawable.ic_setting_terms_and_condition,
                {}),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingTabBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding?.settingTabTiles?.layoutManager = layoutManager
        binding?.settingTabTiles?.adapter = SettingTabAdapter(settingTileField)
        binding?.logoutBtn?.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            var mGoogleSignInClient = activity?.let { it1 -> GoogleSignIn.getClient(it1, gso) }
            FirebaseAuth.getInstance().signOut()
            mGoogleSignInClient?.signOut()
            var intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}