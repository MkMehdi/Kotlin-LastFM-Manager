package ma.polyapps.lastfm.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ma.polyapps.lastfm.MyApplication
import ma.polyapps.lastfm.api.ApiInstance
import ma.polyapps.lastfm.api.ApiService
import ma.polyapps.lastfm.api.model.Album
import ma.polyapps.lastfm.api.model.AlbumDetail
import ma.polyapps.lastfm.api.model.Artist
import ma.polyapps.lastfm.api.model.Track
import ma.polyapps.lastfm.db.entity.AlbumEntity
import ma.polyapps.lastfm.db.entity.ArtistEntity
import ma.polyapps.lastfm.db.entity.TrackEntity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private var apiService: ApiService? = null

    var mArtist: Artist? = null

    var mArtistList: List<Artist>? = null
    var mAlbumList: List<Album>? = null
    var mStoredAlbumList: List<AlbumEntity>? = null
    var mAlbumDetail: AlbumDetail? = null
    var mAlbumStored: AlbumEntity? = null

    var albumImage: ByteArray? = null

    init {
        apiService = ApiInstance.getRetrofitInstance()?.create(ApiService::class.java)
    }

    fun searchByArtist(artist: String): MutableLiveData<List<Artist>> {
        val artistData: MutableLiveData<List<Artist>> = MutableLiveData()

        val request = apiService?.searchArtist(artist)

        /**Log the URL called*/
        Log.d("API", "URL Called ${request?.request()?.url()}")

        request?.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                Log.v("retrofit", "s ${response?.toString()}")
                Log.v("retrofit", "s ${response?.body()}")

                if (response?.body() != null) {

                    val listType = object : TypeToken<List<Artist>>() {}.type
                    val artists = JSONObject(response.body().toString()).getJSONObject("results").getJSONObject("artistmatches").getJSONArray("artist").toString()

                    mArtistList = Gson().fromJson(artists, listType)

                    Log.v("retrofit", "s ${mArtistList!![0].name}")
                }

                artistData.postValue(mArtistList)

            }

        })

        return artistData
    }

    fun getTopAlbums(artist: Artist): MutableLiveData<List<Album>> {
        val albumData: MutableLiveData<List<Album>> = MutableLiveData()

        val request = apiService?.requestAlbums(artist.mbid, artist.name)

        /**Log the URL called*/
        Log.d("API", "URL Called ${request?.request()?.url()}")

        request?.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                Log.v("retrofit", "s ${response?.toString()}")
                Log.v("retrofit", "s ${response?.body()}")

                if (response?.body() != null) {

                    val listType = object : TypeToken<List<Album>>() {}.type
                    val albums = JSONObject(response?.body().toString()).getJSONObject("topalbums").getJSONArray("album").toString()

                    mAlbumList = Gson().fromJson(albums, listType)

                    Log.v("retrofit", "s ${mAlbumList!![0].name}")

                }

                albumData.postValue(mAlbumList)

            }

        })

        return albumData
    }

    fun getAlbumDetail(mbId: String): MutableLiveData<AlbumDetail> {
        val albumDetailData: MutableLiveData<AlbumDetail> = MutableLiveData()

        val request = apiService?.requestAlbum(mbId)

        /**Log the URL called*/
        Log.d("API", "URL Called ${request?.request()?.url()}")

        request?.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<JsonElement>?, response: Response<JsonElement>?) {
                Log.v("retrofit", "s ${response?.toString()}")
                Log.v("retrofit", "s ${response?.body()}")

                if (response?.body() != null) {

                    val albumDetail = JSONObject(response?.body().toString()).getJSONObject("album").toString()
                    mAlbumDetail = Gson().fromJson(albumDetail, AlbumDetail::class.java)

                    Log.v("retrofit", "s ${mAlbumDetail!!.name}")
                }

                albumDetailData.postValue(mAlbumDetail)

            }

        })

        return albumDetailData
    }

    fun storeAlbum(): MutableLiveData<Long>? {
        val inserted: MutableLiveData<Long> = MutableLiveData()
        AsyncTask.execute {
            inserted.postValue(MyApplication().getRepository()?.addAlbum(getAlbumEntity()))
        }
        return inserted
    }

    fun deleteAlbum(): MutableLiveData<Int>? {
        val deleted: MutableLiveData<Int> = MutableLiveData()
        AsyncTask.execute {
            deleted.postValue(MyApplication().getRepository()?.deleteAlbum(mAlbumStored!!))
        }
        return deleted
    }

    fun getStoredAlbums(): LiveData<List<AlbumEntity>>? {
        return MyApplication().getRepository()?.getAlbums()
    }

    private fun getAlbumEntity(): AlbumEntity {

        val name = mAlbumDetail?.name
        val mbid = mAlbumDetail?.mbid
        val url = mAlbumDetail?.url
        val releaseDate = mAlbumDetail?.name
        val image = albumImage
        val artist = mAlbumDetail?.artist
        val tracks = getTrackEntity(mAlbumDetail?.tracks?.tracks)

        return AlbumEntity(name!!, mbid!!, url!!, releaseDate!!, artist!!, image!!, tracks)
    }

    private fun getTrackEntity(tracks: List<Track>?): List<TrackEntity> {
        val listTrack: MutableList<TrackEntity> = ArrayList()
        Log.d("tracks", "bb ${tracks!![1].name}")
        tracks.forEach {
            val name = it.name
            val duration = it.duration
            val mbid = it.mbid
            val url = it.url
            val artist = getArtistEntity(it.artist)
            listTrack.add(TrackEntity(name, duration, mbid, url, artist))
        }

        return listTrack
    }

    private fun getArtistEntity(artist: Artist): ArtistEntity {
        Log.d("artist", "bb ${artist.name}")
        val name = artist.name
        val mbid = artist.mbid
        val url = artist.url
        val image = artist.url
        val streamabl = artist.streamable

        return ArtistEntity(name, mbid, url, image, streamabl)
    }


}