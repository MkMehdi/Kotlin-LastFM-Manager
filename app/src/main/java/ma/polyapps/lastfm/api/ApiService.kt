package ma.polyapps.lastfm.api

import com.google.gson.JsonElement
import ma.polyapps.lastfm.api.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Elmehdi Mellouk on 21/09/18.
 * elmehdi.mellouk@gmail.com
 */

interface ApiService {

    @GET("/2.0/?method=artist.search&api_key=840925b98811280a9f907b6484523fae&format=json")
    fun searchArtist(@Query("artist") artist: String): Call<JsonElement>

    @GET("/2.0/?method=artist.getinfo&api_key=840925b98811280a9f907b6484523fae&format=json")
    fun requestArtistInfo(@Query("mbid") id: String, @Query("lang") language: String): Call<Artist>

    @GET("/2.0/?method=artist.gettopalbums&api_key=840925b98811280a9f907b6484523fae&format=json")
    fun requestAlbums(@Query("mbid") id: String, @Query("artist") artist: String): Call<JsonElement>

    @GET("/2.0/?method=album.getInfo&api_key=840925b98811280a9f907b6484523fae&format=json")
    fun requestAlbum(@Query("mbid") id: String): Call<JsonElement>
}