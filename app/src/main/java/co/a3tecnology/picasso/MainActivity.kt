package co.a3tecnology.picasso

import android.content.Intent
import android.os.Bundle
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.a3tecnology.picasso.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val POSTER = "poster_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.layoutManager = GridLayoutManager(this, 2)

        popularMovies()
    }

    private fun popularMovies() {
        var list: List<Movie>? = null

        val listArray : JSONArray = readJson(R.raw.popular)

        list = readItems(listArray)

        if (list != null) {
            val adapter = MovieAdapter(list, windowManager) { movie ->
                startActivity(
                    Intent(this@MainActivity,  MovieDetalheActive::class.java)
                        .putExtra(POSTER, movie?.posterPath)
                )
            }
            rv_main.adapter = adapter
        }
    }

    private fun readItems(array: JSONArray) : ArrayList<Movie> {
        val list = arrayListOf<Movie>()

        val size = array.length()
        for (i in 0 until size) {
            //guarda objeto que vem da lista
            val ob = array.getJSONObject(i)

            val movie = Movie(
                popularity = ob.getDouble("popularity").toFloat(),
                title = ob.getString("title"),
                posterPath = ob.getString("poster_path"),
                overview = ob.getString("overview"),
                releaseDate = ob.getString("release_date"),
            )

            list.add(movie)
        }

        return list
    }

    private fun readJson(@RawRes id: Int) : JSONArray {
        val inputStream: InputStream = resources.openRawResource(id)
        val json: String = Scanner(inputStream).useDelimiter("\\A").next()
        return JSONObject(json).getJSONArray("results")
    }
}