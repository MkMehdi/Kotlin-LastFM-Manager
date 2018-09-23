package ma.polyapps.lastfm.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class ApiInstance {

    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "http://ws.audioscrobbler.com/"
        public val keyApi = "840925b98811280a9f907b6484523fae"


        /**
         * Create an instance of Retrofit object
         */
        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
    }
}