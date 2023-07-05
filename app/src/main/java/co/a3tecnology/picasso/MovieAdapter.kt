package co.a3tecnology.picasso

import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.a3tecnology.picasso.model.Movie
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlin.math.roundToInt

class MovieAdapter(
    val movieList: List<Movie>,
    val windowManager: WindowManager,
    var listerner: (Movie?) -> Unit

) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.title_txt_View).text = movie.title

            itemView.setOnClickListener {
                listerner(movie)
            }

            //pega a propriedades da tela
            val display = windowManager.defaultDisplay

            //guardará as dimensões da tela
            val sizePointer = Point()
            display.getSize(sizePointer)

            val width = sizePointer.x / 2
            val height = (sizePointer.y / 2.6).roundToInt() // roundToInt transformará no inteiro

            if (movie.posterPath != null) {
                val picasso = Picasso.get()

                // Vermelho (rede) - Azul (disco) / Verde(cache)
                  picasso.setIndicatorsEnabled(true)

                    picasso.load(TMDB_IMAGEURL + movie.posterPath)
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .resize(width, height) // redimensão das images na tela
                    .placeholder(R.drawable.ic_launcher_background) // carregará antes da images , caso a internet esteja lenta
                    .into(itemView.post_image_view)
            }
            else {
                val picasso = Picasso.get()
                picasso.setIndicatorsEnabled(true)

                picasso.load(R.drawable.ic_launcher_background)
                    .noFade()
                    .resize(width, height)
                    .into(itemView.post_image_view)
            }
        }
    }

    companion object {
        const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
    }
}



