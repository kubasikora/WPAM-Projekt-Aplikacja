package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.findFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_match_detail.*
import kotlinx.android.synthetic.main.fragment_match_detail.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.ViewPageAdapter
import pw.wpam.polityper.services.BetService
import pw.wpam.polityper.models.MatchStats
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID

class MatchDetailFragment : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mStats: MatchStats? = null
    private var mapSet: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_match_detail, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map)!! as SupportMapFragment
        mapFragment.getMapAsync(this)
        view.map.isVisible = false

        val matchId = this.arguments?.getInt("matchId")

        val playerOnePagerAdapter = ViewPageAdapter(this.requireActivity())
        view.viewPagerPlayerOneForm.adapter = playerOnePagerAdapter
        playerOnePagerAdapter.addFragment(TeamFormFragment(ArrayList<String>()), "playerOne")

        val playerTwoPagerAdapter = ViewPageAdapter(this.requireActivity())
        view.viewPagerPlayerTwoForm.adapter = playerTwoPagerAdapter
        playerTwoPagerAdapter.addFragment(TeamFormFragment(ArrayList<String>()), "playerTwo")

        view.loadingSpinner.isVisible = true
        BetService.getMatchStatistics(matchId!!) { success, stats: MatchStats? ->
            mStats = stats!!

            view.loadingSpinner.isVisible = false
            view.map.isVisible = true

            view.findViewById<TextView>(R.id.statsPlayerOne).text = stats?.playerOne?.name
            view.findViewById<TextView>(R.id.statsPlayerTwo).text = stats?.playerTwo?.name
            playerOnePagerAdapter.update(stats?.playerOneForm!!)
            playerTwoPagerAdapter.update(stats?.playerTwoForm!!)

            if(mMap != null && !mapSet){
                initMap()
            }
        }
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        if(mStats != null && !mapSet){
            initMap()
        }
    }

    fun initMap(){
        mapSet = true
        val position = LatLng(mStats?.venue?.latitude!!, mStats?.venue?.longitude!!)
        Log.d("MAP", mStats?.venue?.name!!)
        val marker = mMap?.addMarker(MarkerOptions().position(position).title(mStats?.venue?.name!!))
        marker?.showInfoWindow()
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0f))
        mMap?.mapType = MAP_TYPE_HYBRID
    }
}