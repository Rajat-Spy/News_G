package com.example.newsg

import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject

package com.example.nearbyplaces
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FetchData : AsyncTask<Any?, String?, String?>() {
    var googleNearByPlacesData: String? = null
    var googleMap: GoogleMap? = null
    var url: String? = null
    override fun onPostExecute(s: String?) {
        try {
            val jsonObject = JSONObject(s)
            val jsonArray = jsonObject.getJSONArray("results")
            for (i in 0 until jsonArray.length()) {
                val jsonObject1 = jsonArray.getJSONObject(i)
                val getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location")
                val lat = getLocation.getString("lat")
                val lng = getLocation.getString("lng")
                val getName = jsonArray.getJSONObject(i)
                val name = getName.getString("name")
                val latlng = LatLng(lat.toDouble(), lng.toDouble())
                val markerOptions = MarkerOptions()
                markerOptions.title(name)
                markerOptions.position(latlng)
                googleMap.addMarker(markerOptions)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15))
            }
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }

    protected override fun doInBackground(vararg objects: Any): String? {
        googleMap = objects[0] as GoogleMap
        url = objects[1] as String
        val downloadUrl = DownloadUrl()
        googleNearByPlacesData = downloadUrl.readTheUrl(url)
        return googleNearByPlacesData
    }
}