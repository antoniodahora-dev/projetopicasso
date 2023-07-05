package co.a3tecnology.picasso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.a3tecnology.picasso.MovieAdapter.Companion.TMDB_IMAGEURL
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.activity_movie_detalhe_active.*

class MovieDetalheActive : AppCompatActivity() {

    private val picasso = Picasso.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detalhe_active)

        configureUi()
    }

    private fun configureUi() {
        picasso.load(TMDB_IMAGEURL
                + intent.getStringExtra(MainActivity.POSTER))
            .error(R.drawable.ic_launcher_background)
            .into(movie_detail_image_view)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.grayscale -> setGrayScale()
            R.id.blur -> setBlur()
            R.id.test -> setAlfa()
            else -> println("error")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBlur() {
        picasso.load(TMDB_IMAGEURL
                + intent.getStringExtra(MainActivity.POSTER))
            .transform(BlurTransformation(this))
            .error(R.drawable.ic_launcher_background)
            .into(movie_detail_image_view)
    }

    private fun setAlfa() {
        picasso.load(TMDB_IMAGEURL
                + intent.getStringExtra(MainActivity.POSTER))
            .transform(AlfaTransformation(0.1f))
            .error(R.drawable.ic_launcher_background)
            .into(movie_detail_image_view)
    }

    private fun setGrayScale() {
        picasso.load(TMDB_IMAGEURL
                + intent.getStringExtra(MainActivity.POSTER))
            .transform(GrayscaleTransformation())
            .error(R.drawable.ic_launcher_background)
            .into(movie_detail_image_view)
    }
}